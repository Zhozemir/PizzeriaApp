package com.example.pizzeria.controllers;

import com.example.pizzeria.controllers.validators.OrderControllerValidator;
import com.example.pizzeria.controllers.requests.OrderCreateRequest;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.enumerators.OrderStatus;
import com.example.pizzeria.mappers.OrderMapper;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.printing.OrderPrinter;
import com.example.pizzeria.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderControllerValidator orderControllerValidator;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderControllerValidator orderControllerValidator, OrderMapper orderMapper) {

        this.orderService = orderService;
        this.orderControllerValidator = orderControllerValidator;
        this.orderMapper = orderMapper;

    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {

        orderControllerValidator.validateCreateOrder(orderCreateRequest);

        orderService.createOrder(orderCreateRequest.getProductIds());

        return ResponseEntity.ok("Поръчката е създадена. Очаквано време за доставка: ~30 минути.");

    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {

        List<OrderDTO> orders = orderService.getAllOrders().stream()
                .map(orderMapper::toDTO)
                .toList();
        return ResponseEntity.ok(orders);

    }

    //Ендпойнт за отпечатване (с използване на ascii)
    @GetMapping("/print")
    public String printOrders() {

        List<Order> orders = orderService.getAllOrders();

        if(orders.isEmpty())
            return "Няма поръчки";

        return OrderPrinter.getPrintedOrders(orders);
    }


    @GetMapping("/my")
    public String getMyOrders() {

        List<Order> orders = orderService.getOrdersByUser();

        if(orders.isEmpty())
            return "Няма поръчки";

        return OrderPrinter.getPrintedOrders(orders);
    }

    @GetMapping("/my/json")
    public ResponseEntity<List<OrderDTO>> getMyOrdersJson(@RequestParam("userId") Long userId) {

        List<OrderDTO> dtos = orderService.getOrdersByUserId(userId)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/delivered/print")
    public String printDeliveredOrders(){

        List<Order> deliveredOrders = orderService.getOrdersByStatus(OrderStatus.DELIVERED);

        if(deliveredOrders.isEmpty())
            return "Няма завършени поръчки";

        return OrderPrinter.getPrintedOrders(deliveredOrders);

    }

    // only current customer delivered orders
    @GetMapping("my/delivered/print")
    public String printMyDeliveredOrders(){

        List<Order> myOrders = orderService.getOrdersByUser();

        List<Order> delivered = myOrders.stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .toList();

        if(delivered.isEmpty())
            return "Няма завършени поръчки за повторение.";

        return OrderPrinter.getPrintedOrders(delivered);

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {

        orderControllerValidator.validateUpdateOrderStatus(id, status);

        orderService.updateOrderStatus(id, status);

        return ResponseEntity.ok("Статусът на поръчката е обновен.");

    }

    @GetMapping("/period")
    public ResponseEntity<List<OrderDTO>> getOrdersByPeriod(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {

        List<OrderDTO> orders = orderService.getOrdersByPeriod(startTime, endTime).stream()
                .map(orderMapper::toDTO)
                .toList();
        return ResponseEntity.ok(orders);

    }

    @GetMapping("/period/print")
    public ResponseEntity<String> getOrdersByPeriodPrinted(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {

        List<Order> orders = orderService.getOrdersByPeriod(startTime, endTime);

        return orders.isEmpty()
                ? ResponseEntity.ok("Няма поръчки в този период")
                : ResponseEntity.ok(OrderPrinter.getPrintedOrders(orders));
    }

    @PostMapping("/{id}/repeat")
    public ResponseEntity<String> repeatOrder(@PathVariable Long id) {

        orderControllerValidator.validateRepeatOrder(id);

        orderService.repeatOrder(id);

        return ResponseEntity.ok("Поръчката е повторена. Очаквано време за доставка: ~30 минути.");

    }

}
