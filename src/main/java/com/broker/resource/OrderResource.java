package com.broker.resource;

import com.broker.repo.entity.Order;
import com.broker.repo.enums.OrderStatus;
import com.broker.resource.req.OrderSearchRequest;
import com.broker.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Slf4j
public class OrderResource {

    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderResource.class);


    // ثبت سفارش جدید (POST /orders)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        logger.info("order : {}", order);
        Order newOrder = orderService.createOrder(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    // دریافت لیست سفارش‌ها (post /orders)
    @PostMapping(path = "/search", produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<Order>> getOrders(@RequestBody  OrderSearchRequest request) {
        List<Order> orders = orderService.getAllOrders(request);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // دریافت جزئیات یک سفارش خاص (GET /orders/{orderId})
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Optional<Order> order = orderService.getOrderById(orderId);
        return order.map(o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // به‌روزرسانی وضعیت سفارش (PATCH /orders/{orderId}/status)
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return updatedOrder != null ? new ResponseEntity<>(updatedOrder, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    

}
