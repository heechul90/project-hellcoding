package com.heech.hellcoding.core.shop.orderItem.repository;

import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.heech.hellcoding.core.shop.orderItem.domain.QOrderItem.orderItem;

@Repository
public class OrderItemQueryRepository {

    private final JPAQueryFactory queryFactory;

    public OrderItemQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 주문상품 목록 조회
     */
    public Page<OrderItem> findOrderItems(OrderItemSearchCondition condition, Pageable pageable) {
        List<OrderItem> content = getOrderItemList(condition, pageable);

        JPAQuery<Long> count = getOrderitemListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * 주문상품 목록
     */
    private List<OrderItem> getOrderItemList(OrderItemSearchCondition condition, Pageable pageable) {
        return queryFactory
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
    }

    /**
     * 주문상품 목록 카운트
     * @return
     */
    private JPAQuery<Long> getOrderitemListCount(OrderItemSearchCondition condition) {
        return queryFactory
                .select(orderItem.count())
                .from(orderItem)
                .where(
                        searchOrderIdEq(condition.getSearchOrderId()),
                        searchOrderPriceGoe(condition.getSearchOrderPriceGoe()),
                        searchOrderPriceLoe(condition.getSearchOrderPriceLoe())
                );
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
