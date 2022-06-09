package com.heech.hellcoding.core.shop.orderItem.repository;

import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemSearchCondition;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.el.parser.BooleanNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.heech.hellcoding.core.shop.orderItem.domain.QOrderItem.*;

public class OrderItemRepositoryImpl implements OrderItemRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public OrderItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderItem> findOrderItems(OrderItemSearchCondition condition, Pageable pageable) {
        List<OrderItem> content = getOrderItemList(condition, pageable);

        JPAQuery<Integer> count = getOrderitemListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * 주문상품 목록
     */
    private List<OrderItem> getOrderItemList(OrderItemSearchCondition condition, Pageable pageable) {
        List<OrderItem> content = queryFactory
                .select(orderItem)
                .from(orderItem)
                .leftJoin(orderItem.item)
                .fetchJoin()
                .where(
                        searchOrderIdEq(condition.getSearchOrderId()),
                        searchOrderPriceGoe(condition.getSearchOrderPriceGoe()),
                        searchOrderPriceLoe(condition.getSearchOrderPriceLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

    /**
     * 주문상품 목록 카운트
     * @return
     */
    private JPAQuery<Integer> getOrderitemListCount(OrderItemSearchCondition condition) {
        JPAQuery<Integer> count = queryFactory
                .select(orderItem.count)
                .from(orderItem)
                .where(
                        searchOrderIdEq(condition.getSearchOrderId()),
                        searchOrderPriceGoe(condition.getSearchOrderPriceGoe()),
                        searchOrderPriceLoe(condition.getSearchOrderPriceLoe())
                );
        return count;
    }

    /**
     * orderitem.order.id == searchOrderId
     */
    private BooleanExpression searchOrderIdEq(Long orderId) {
        return orderId != null ? orderItem.order.id.eq(orderId) : null;
    }

    /**
     * orderItem.orderPrice >= searchOrderPriceGoe
     */
    private BooleanExpression searchOrderPriceGoe(Integer searchOrderPriceGoe) {
        return searchOrderPriceGoe != null ? orderItem.orderPrice.goe(searchOrderPriceGoe) : null;
    }

    /**
     * orderItem.orderPrice <= searchOrderPriceLoe
     */
    private BooleanExpression searchOrderPriceLoe(Integer searchOrderPriceLoe) {
        return searchOrderPriceLoe != null ? orderItem.orderPrice.loe(searchOrderPriceLoe) : null;
    }

}
