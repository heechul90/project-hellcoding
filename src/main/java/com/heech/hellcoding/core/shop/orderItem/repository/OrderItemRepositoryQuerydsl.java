package com.heech.hellcoding.core.shop.orderItem.repository;

import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemRepositoryQuerydsl {

    Page<OrderItem> findOrderItems(OrderItemSearchCondition condition, Pageable pageable);
}
