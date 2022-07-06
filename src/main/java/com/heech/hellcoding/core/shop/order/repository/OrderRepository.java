package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.shop.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
