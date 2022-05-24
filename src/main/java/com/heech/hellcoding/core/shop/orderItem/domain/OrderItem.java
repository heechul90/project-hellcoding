package com.heech.hellcoding.core.shop.orderItem.domain;

import com.heech.hellcoding.core.shop.item.domain.Item;
import com.heech.hellcoding.core.shop.order.domain.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "orderItem_seq_generator",
        sequenceName = "orderItem_seq",
        initialValue = 1, allocationSize = 100
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderItem_seq_generator")
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    /** 주문가격 */
    private int orderPrice;
    /** 주문수량 */
    private int count;

    //=== 생성 메서드드 ===//
}
