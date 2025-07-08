package com.example.pizzeria.services.impl;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.enumerators.OrderStatus;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.Product;
import com.example.pizzeria.models.User;
import com.example.pizzeria.repositories.interfaces.OrderDAO;
import com.example.pizzeria.repositories.interfaces.ProductDAO;
import com.example.pizzeria.services.DelayedOrder;
import com.example.pizzeria.services.exceptions.InvalidOrderException;
import com.example.pizzeria.services.exceptions.InvalidProductException;
import com.example.pizzeria.services.exceptions.OrderNotFoundException;
import com.example.pizzeria.services.exceptions.OrderProcessingException;
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
                        Long id = delayed.getOrderId();

                        synchronized (dbLock){
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
    public Order createOrder(List<Long> productIds) {

        User currentUser = ConsoleSession.getCurrentUser();

        if (currentUser == null || currentUser.getId() == null) {
            throw new InvalidOrderException("Не сте влезли в системата.");
        }

        List<Product> products = productIds.stream()
                .map(productDAO::findActiveById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        if(products.isEmpty())
            throw new InvalidOrderException("Няма валидни продукти за поръчка.");

        Order order = new Order();
        order.setUser(currentUser);
        order.setStatus(OrderStatus.IN_PROGRESS);
        products.forEach(order::addProduct);

        synchronized (dbLock) {

            try{
                if(!orderDAO.save(order)){
                    throw new OrderProcessingException("Неуспешно създаване на поръчката.", null);
                }
            } catch (Exception ex){
                throw new OrderProcessingException("Грешка при запис на поръчката в базата.", ex);
            }

        }

        Long id = order.getId();
        queue.put(new DelayedOrder(id, 30, TimeUnit.SECONDS));
        return order;

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
   public void updateOrderStatus(Long orderId, OrderStatus newStatus) {

        boolean ok;

        try {
            ok = orderDAO.updateStatus(orderId, newStatus);
        } catch (Exception ex) {
            throw new OrderProcessingException("Грешка при обновяване статуса в базата.", ex);
        }
        if (!ok) {
            throw new OrderNotFoundException(orderId);
        }

    }

    @Override
    public List<Order> getOrdersByPeriod(LocalDateTime start, LocalDateTime end) {
        return orderDAO.findByCreatedOnBetween(start, end);
    }

    @Override
    public Order repeatOrder(Long orderId) {

        Optional<Order> orderOpt = orderDAO.findById(orderId);

        Order original = orderOpt
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if(original.getStatus() != OrderStatus.DELIVERED){
            throw new InvalidOrderException("Поръчка с ID=" + orderId + " още не е доставена.");
        }

        Order newOrder = new Order();
        newOrder.setUser(original.getUser());
        original.getProducts().forEach(newOrder::addProduct);

        synchronized (dbLock){

            try{

                if(!orderDAO.save(newOrder)){
                    throw new OrderProcessingException("Неуспешно повторение на поръчка.", null);
                }

            } catch (Exception ex){
                throw new OrderProcessingException("Грешка при запис на повторената поръчка." , ex);
            }

            queue.put(new DelayedOrder(newOrder.getId(), 30, TimeUnit.SECONDS));

        }

        return newOrder;

    }

    @Override
    public List<Order> getDeliveredAfter (LocalDateTime since){
        return orderDAO.findDeliveredAfter(since);
    }

}
