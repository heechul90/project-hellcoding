package com.heech.hellcoding.core.shop.orderItem.repository;

import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemRepositoryQuerydsl {
}
