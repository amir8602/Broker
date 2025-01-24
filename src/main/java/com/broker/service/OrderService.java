package com.broker.service;

import com.broker.repo.entity.Order;
import com.broker.repo.enums.OrderStatus;
import com.broker.resource.req.OrderSearchRequest;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    // ثبت سفارش جدید
    Order createOrder(Order order);

    // دریافت لیست تمام سفارش‌ها
    List<Order> getAllOrders(OrderSearchRequest request);

    // دریافت جزئیات یک سفارش خاص
    Optional<Order> getOrderById(Long orderId);

    // به‌روزرسانی وضعیت سفارش
    Order updateOrderStatus(Long orderId, OrderStatus status);

    void update(Order order);
}
