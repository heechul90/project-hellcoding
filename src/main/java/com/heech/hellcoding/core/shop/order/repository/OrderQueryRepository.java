package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.domain.OrderStatus;
import com.heech.hellcoding.core.shop.order.dto.OrderSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.heech.hellcoding.core.member.domain.QMember.member;
import static com.heech.hellcoding.core.shop.order.domain.QOrder.order;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 주문 목록 조회
     */
    public Page<Order> findOrders(OrderSearchCondition condition, Pageable pageable) {
        List<Order> content = getOrderList(condition, pageable);

        JPAQuery<Long> count = getOrderListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * 주문 목록
     */
    private List<Order> getOrderList(OrderSearchCondition condition, Pageable pageable) {
        return queryFactory
                .select(order)
                .from(order)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchOrderStatusEq(condition.getSearchOrderStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 주문 목록 카운트
     */
    private JPAQuery<Long> getOrderListCount(OrderSearchCondition condition) {
        return queryFactory
                .select(order.count())
                .from(order)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchOrderStatusEq(condition.getSearchOrderStatus())
                );
    }

    /**
     * name like '%searchKeyword%'
     * title like '%searchKeyword%'
     * content like '%searchKeyword%'
     */
    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (!hasText(searchKeyword)) return null;
        return null;
    }

    /**
     * searchOrderStatus == order.status
     */
    private BooleanExpression searchOrderStatusEq(OrderStatus searchOrderStatus) {
        return searchOrderStatus != null ? order.status.eq(searchOrderStatus) : null;
    }
}
