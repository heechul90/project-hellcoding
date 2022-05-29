package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.domain.QOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Order> search(Order order, Pageable pageable) {
        return queryFactory
                .selectFrom(QOrder.order)
                .fetch();
    }
}
