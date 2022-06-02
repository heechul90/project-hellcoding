package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.dto.OrderSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryQuerydsl {

    Page<Order> findOrders(OrderSearchCondition condition, Pageable pageable);

}
