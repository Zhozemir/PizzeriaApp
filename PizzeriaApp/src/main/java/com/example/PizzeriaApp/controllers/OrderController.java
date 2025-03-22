package com.example.PizzeriaApp.controllers;

import com.example.PizzeriaApp.controllers.validators.OrderControllerValidator;
import com.example.PizzeriaApp.controllers.requests.OrderCreateRequest;
import com.example.PizzeriaApp.dto.OrderDTO;
import com.example.PizzeriaApp.enumerators.OrderStatus;
import com.example.PizzeriaApp.mappers.OrderMapper;
import com.example.PizzeriaApp.models.Order;
import com.example.PizzeriaApp.printing.OrderPrinter;
import com.example.PizzeriaApp.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

        //boolean isValid = orderControllerValidator.validateCreateOrder(orderCreateRequest);

        orderControllerValidator.validateCreateOrder(orderCreateRequest);

        //if(!isValid)
           // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Грешни данни.");

        //boolean success = orderService.createOrder(orderCreateRequest.getProductIds());
        //return success
                //? ResponseEntity.ok("Поръчката е създадена. Очаквано време за доставка: ~30 минути.")
                //: ResponseEntity.badRequest().body("Грешка при създаването на поръчката");

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


    @GetMapping("/delivered/print")
    public String printDeliveredOrders(){

        List<Order> deliveredOrders = orderService.getOrdersByStatus(OrderStatus.DELIVERED);

        if(deliveredOrders.isEmpty())
            return "Няма завършени поръчки";

        return OrderPrinter.getPrintedOrders(deliveredOrders);

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {

        //boolean isValid = orderControllerValidator.validateUpdateOrderStatus(id, status);

        orderControllerValidator.validateUpdateOrderStatus(id, status);

        //if(!isValid)
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Грешни данни.");

        //boolean success = orderService.updateOrderStatus(id, status);
        //return success
                //? ResponseEntity.ok("Статусът на поръчката е обновен")
                //: ResponseEntity.badRequest().body("Грешка при обновяването на статуса");

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

        //boolean isValid = orderControllerValidator.validateRepeatOrder(id);

        orderControllerValidator.validateRepeatOrder(id);

        //if(!isValid)
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Грешни данни.");

        //boolean success = orderService.repeatOrder(id);
        //return success
                //? ResponseEntity.ok("Поръчката е повторена. Очаквано време за доставка: ~30 минути.")
                //: ResponseEntity.badRequest().body("Грешка при повторяване на поръчката");

        return ResponseEntity.ok("Поръчката е повторена. Очаквано време за доставка: ~30 минути.");

    }

}
