package com.heech.hellcoding.core.shop.delivery.repository;

import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.delivery.dto.DeliverySearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.heech.hellcoding.core.shop.delivery.domain.QDelivery.*;

public class DeliveryRepositoryImpl implements DeliveryRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public DeliveryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Delivery> findDeliveries(DeliverySearchCondition condition, Pageable pageable) {
        List<Delivery> content = getDeliveryList(condition, pageable);

        JPAQuery<Long> count = getDeliveryListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * 배송 목록
     */
    private List<Delivery> getDeliveryList(DeliverySearchCondition condition, Pageable pageable) {
        List<Delivery> content = queryFactory
                .select(delivery)
                .from(delivery)
                .where(
                        searchDeliveryStatusEq(condition.getSearchDeliveryStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

    /**
     * 배송 목록 카운트
     */
    private JPAQuery<Long> getDeliveryListCount(DeliverySearchCondition condition) {
        JPAQuery<Long> count = queryFactory
                .select(delivery.count())
                .from(delivery)
                .where(
                        searchDeliveryStatusEq(condition.getSearchDeliveryStatus())
                );
        return count;
    }

    /**
     * delivery.status == searchDeliveryStatus
     */
    private BooleanExpression searchDeliveryStatusEq(DeliveryStatus deliveryStatus) {
        if (DeliveryStatus.READY.equals(deliveryStatus)) {
            return delivery.status.eq(deliveryStatus);
        } else if (DeliveryStatus.DELIVERY.equals(deliveryStatus)) {
            return delivery.status.eq(deliveryStatus);
        } else if (DeliveryStatus.COMPLETE.equals(deliveryStatus)) {
            return delivery.status.eq(deliveryStatus);
        } else {
            return null;
        }
    }
}
