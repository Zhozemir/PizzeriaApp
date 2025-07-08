package com.example.pizzeria.controllers;

import com.example.pizzeria.controllers.validators.OrderControllerValidator;
import com.example.pizzeria.controllers.requests.OrderCreateRequest;
import com.example.pizzeria.dto.OrderDTO;
import com.example.pizzeria.enumerators.OrderStatus;
import com.example.pizzeria.mappers.OrderMapper;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {

        orderControllerValidator.validateCreateOrder(orderCreateRequest);

        Order created  = orderService.createOrder(orderCreateRequest.getProductIds());
        OrderDTO dto = orderMapper.toDTO(created);
        URI location  = URI.create("/api/orders/" + dto.getId());

        return ResponseEntity
                .created(location)
                .body(dto);

    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {

        List<OrderDTO> orders = orderService.getAllOrders().stream()
                .map(orderMapper::toDTO)
                .toList();
        return ResponseEntity.ok(orders);

    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderDTO>> getMyOrders(){

        List<OrderDTO> dtos = orderService.getOrdersByUser()
                .stream()
                .map(orderMapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);

    }

    @GetMapping("/my/delivered")
    public ResponseEntity<List<OrderDTO>> getMyDeliveredOrders(){

        List<OrderDTO> dtos = orderService.getOrdersByUser()
                .stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .map(orderMapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);

    }

    @GetMapping("/my/json")
    public ResponseEntity<List<OrderDTO>> getMyOrdersJson(@RequestParam("userId") Long userId) {

        List<OrderDTO> dtos = orderService.getOrdersByUserId(userId)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {

        orderControllerValidator.validateUpdateOrderStatus(id, status);

        orderService.updateOrderStatus(id, status);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/period")
    public ResponseEntity<List<OrderDTO>> getOrdersByPeriod(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {

        List<OrderDTO> orders = orderService.getOrdersByPeriod(startTime, endTime).stream()
                .map(orderMapper::toDTO)
                .toList();
        return ResponseEntity.ok(orders);

    }

    @PostMapping("/{id}/repeat")
    public ResponseEntity<OrderDTO> repeatOrder(@PathVariable Long id) {

        orderControllerValidator.validateRepeatOrder(id);

        Order repeated = orderService.repeatOrder(id);
        OrderDTO dto = orderMapper.toDTO(repeated);

        URI location  = URI.create("/api/orders" + dto.getId());

        return ResponseEntity
                .created(location)
                .body(dto);

    }

    @GetMapping("/my/delivered/after")
    public ResponseEntity<List<OrderDTO>> getMyDeliveredAfter(@RequestParam ("since") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since){

        List<OrderDTO> dtos = orderService.getDeliveredAfter(since)
                .stream()
                .map(orderMapper::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);

    }

}
