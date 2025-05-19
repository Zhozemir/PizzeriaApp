package com.example.pizzeria.services.impl;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.enumerators.OrderStatus;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.Product;
import com.example.pizzeria.models.User;
import com.example.pizzeria.repositories.interfaces.OrderDAO;
import com.example.pizzeria.repositories.interfaces.ProductDAO;
import com.example.pizzeria.services.DelayedOrder;
import com.example.pizzeria.services.interfaces.OrderService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


// бизнес логика, обработват се заявки
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;

    private DelayQueue<DelayedOrder> queue = new DelayQueue<>();
    private final ExecutorService workers = Executors.newFixedThreadPool(3);
    private final Object dbLock = new Object();

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, ProductDAO productDAO){

        this.orderDAO = orderDAO;
        this.productDAO = productDAO;

    }

    @PostConstruct
    public void initProcessing(){

        for(int i = 0; i < 3; i++){

            workers.submit(()->{
                while(!Thread.currentThread().isInterrupted()){

                    try{

                        DelayedOrder delayed = queue.take();
                        //Order o  = delayed.getOrder();
                        Long id = delayed.getOrderId();
                        synchronized (dbLock){
                            //o.setStatus(OrderStatus.DELIVERED);
                            //orderDAO.save(o);
                            orderDAO.updateStatus(id, OrderStatus.DELIVERED);
                        }
                    } catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }
            });

        }

    }

    @Override
    public boolean createOrder(List<Long> productIds) {

        User currentUser = ConsoleSession.getCurrentUser();

        if (currentUser == null || currentUser.getId() == null) {

            System.out.println("Не сте влезли в системата.");
            return false;

        }

        List<Product> products = productIds.stream()
                .map(productDAO::findActiveById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        if(products.isEmpty())
            return false;

        Order order = new Order();
        order.setUser(currentUser);
        order.setStatus(OrderStatus.IN_PROGRESS);
        products.forEach(order::addProduct);

        synchronized (dbLock) {
            orderDAO.save(order);
        }

        Long id = order.getId();
        queue.put(new DelayedOrder(id, 30, TimeUnit.SECONDS));
        return true;

    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderDAO.findByStatus(status);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    @Override
    public List<Order> getOrdersByUser(){

        User currentUser = ConsoleSession.getCurrentUser();
        return orderDAO.findByUserId(currentUser.getId());

    }

    @Override
    public List<Order> getOrdersByUserId(Long userId){
        return orderDAO.findByUserId(userId);
    }

    @Override
    public boolean updateOrderStatus(Long orderId, OrderStatus newStatus) {
        return orderDAO.updateStatus(orderId, newStatus);
    }

    @Override
    public List<Order> getOrdersByPeriod(LocalDateTime start, LocalDateTime end) {
        return orderDAO.findByCreatedOnBetween(start, end);
    }

    @Override
    public boolean repeatOrder(Long orderId) {

        Optional<Order> orderOpt = orderDAO.findById(orderId);

        if(orderOpt.isPresent()){

            Order original = orderOpt.get();

            if(original.getStatus() != OrderStatus.DELIVERED)
                return false;

//            List<Long> productIds = original.getProducts()
//                    .stream()
//                    .map(Product::getId)
//                    .collect(Collectors.toList());
//            return createOrder(productIds);

            Order newOrder = new Order();
            newOrder.setUser(original.getUser());
            original.getProducts().forEach(newOrder::addProduct);

            synchronized (dbLock) {
                return orderDAO.save(newOrder);
            }

        }

        return false;
    }

}
