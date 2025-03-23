package com.example.pizzeria.services.impl;

import com.example.pizzeria.console.ConsoleSession;
import com.example.pizzeria.enumerators.OrderStatus;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.Product;
import com.example.pizzeria.models.User;
import com.example.pizzeria.repositories.interfaces.OrderDAO;
import com.example.pizzeria.repositories.interfaces.ProductDAO;
import com.example.pizzeria.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


// бизнес логика, обработват се заявки
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, ProductDAO productDAO){

        this.orderDAO = orderDAO;
        this.productDAO = productDAO;

    }

    @Override
    public boolean createOrder(List<Long> productIds) {

//        Order order = new Order();
//        for(Long id : productIds) {
//
//            Optional<Product> productOpt = productDAO.findById(id);
//            productOpt.ifPresent(order::addProduct);
//
//        }
//
//        return orderDAO.save(order);

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
        products.forEach(order::addProduct);

        return orderDAO.save(order);

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

            return orderDAO.save(newOrder);

        }

        return false;
    }

}
