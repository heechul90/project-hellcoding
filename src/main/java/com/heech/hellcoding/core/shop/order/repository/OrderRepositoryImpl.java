package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.domain.OrderStatus;
import com.heech.hellcoding.core.shop.order.domain.QOrder;
import com.heech.hellcoding.core.shop.order.dto.OrderSearchCondition;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.heech.hellcoding.core.shop.order.domain.QOrder.*;

public class OrderRepositoryImpl implements OrderRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Order> findOrders(OrderSearchCondition condition, Pageable pageable) {
        List<Order> content = queryFactory
                .select(order)
                .from(order)
                .leftJoin(order.orderItems)
                .fetchJoin()
                .where(
                        searchOrderStatusEq(condition.getSearchOrderStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(order.count())
                .from(order)
                .where(
                        searchOrderStatusEq(condition.getSearchOrderStatus())
                );
        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private BooleanExpression searchOrderStatusEq(OrderStatus searchOrderStatus) {
        return searchOrderStatus != null ? order.status.eq(searchOrderStatus) : null;
    }
}
