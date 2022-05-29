package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.shop.order.domain.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> search(Order order, Pageable pageable);

}
