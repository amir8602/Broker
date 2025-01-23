package com.broker.repo.dao;

import com.broker.repo.entity.Order;
import com.broker.repo.enums.OrderAction;
import com.broker.repo.enums.OrderStatus;
import com.broker.repo.enums.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByActionInAndTypeInAndStatusIn(List<OrderAction> actions, List<OrderType> types, List<OrderStatus> statuses);


}
