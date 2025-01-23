package com.broker.service.impl;

import com.broker.repo.dao.OrderRepository;
import com.broker.repo.entity.Order;
import com.broker.repo.enums.OrderAction;
import com.broker.repo.enums.OrderStatus;
import com.broker.repo.enums.OrderType;
import com.broker.resource.req.OrderSearchRequest;
import com.broker.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderRepository orderRepository;

    // ثبت سفارش جدید
    @Transactional
    @Override
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.ACTIVE);
        Long v = order.getQuantity() * order.getAmount();
        order.setTotalAmount(v);
        return orderRepository.save(order);
    }

    // دریافت لیست تمام سفارش‌ها
    @Override
    public List<Order> getAllOrders(OrderSearchRequest request) {
        if (request.getSearchOption().isEmpty()) {
            return orderRepository.findAll();
        } else {
            List<Integer> searchOption = request.getSearchOption();
            List<OrderAction> actions = new ArrayList<>();
            actions.add(OrderAction.BUY);
            actions.add(OrderAction.SELL);
            List<OrderType> types = new ArrayList<>();
            types.add(OrderType.COIN);
            types.add(OrderType.TAMAM);
            types.add(OrderType.ROB);
            types.add(OrderType.NIM);
            List<OrderStatus> statuses = new ArrayList<>();


            if (searchOption.contains(1)) {


                types.remove(OrderType.TAMAM);
                types.remove(OrderType.ROB);
                types.remove(OrderType.NIM);
            }
            if (searchOption.contains(2)) {
                types.remove(OrderType.COIN);
            }
            if (searchOption.contains(1) && searchOption.contains(2)){
                actions.add(OrderAction.BUY);
                actions.add(OrderAction.SELL);
                types.add(OrderType.COIN);
                types.add(OrderType.TAMAM);
                types.add(OrderType.ROB);
                types.add(OrderType.NIM);
            }
            if (searchOption.contains(5)) {
                actions.remove(OrderAction.SELL);
            }
            if (searchOption.contains(6)) {
                actions.remove(OrderAction.BUY);
            }

            if (searchOption.contains(5) && searchOption.contains(6)){
                actions.add(OrderAction.BUY);
                actions.add(OrderAction.SELL);
            }

            statuses.add(OrderStatus.ACTIVE);

            return orderRepository.findByActionInAndTypeInAndStatusIn(actions, types, statuses);

        }
    }

    // دریافت جزئیات یک سفارش خاص
    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    // به‌روزرسانی وضعیت سفارش
    @Transactional
    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order updatedOrder = order.get();
            updatedOrder.setStatus(status);
            updatedOrder.setUpdatedAt(Instant.now().toEpochMilli());  // زمان به‌روزرسانی
            return orderRepository.save(updatedOrder);
        }
        return null;  // اگر سفارش پیدا نشد
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
