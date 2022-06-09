package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
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
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.heech.hellcoding.core.shop.order.domain.QOrder.*;
import static org.springframework.util.StringUtils.*;

public class OrderRepositoryImpl implements OrderRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Order> findOrders(OrderSearchCondition condition, Pageable pageable) {
        List<Order> content = getOrderList(condition, pageable);

        JPAQuery<Long> count = getOrderListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private List<Order> getOrderList(OrderSearchCondition condition, Pageable pageable) {
        List<Order> content = queryFactory
                .select(order)
                .from(order)
                //.leftJoin(order.orderItems)
                //.fetchJoin()
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchOrderStatusEq(condition.getSearchOrderStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

    private JPAQuery<Long> getOrderListCount(OrderSearchCondition condition) {
        JPAQuery<Long> count = queryFactory
                .select(order.count())
                .from(order)
                .where(
                        searchOrderStatusEq(condition.getSearchOrderStatus())
                );
        return count;
    }

    /**
     *
     */
    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (searchCondition == null || !hasText(searchKeyword)) {
            return null;
        }
        return null;
    }

    /**
     * searchOrderStatus == order.status
     */
    private BooleanExpression searchOrderStatusEq(OrderStatus searchOrderStatus) {
        return searchOrderStatus != null ? order.status.eq(searchOrderStatus) : null;
    }
}
