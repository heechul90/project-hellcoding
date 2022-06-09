package com.heech.hellcoding.core.shop.orderItem.domain;

import com.heech.hellcoding.core.shop.item.info.domain.Item;
import com.heech.hellcoding.core.shop.order.domain.Order;
import lombok.*;

import javax.persistence.*;

@Entity
/*@SequenceGenerator(
        name = "orderItem_seq_generator",
        sequenceName = "orderItem_seq",
        initialValue = 1, allocationSize = 100
)*/
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    /** 주문가격 */
    private int orderPrice;
    /** 주문수량 */
    private int count;

    //===연관관계 편의 메서드===//
    /*public void setOrder(Order order) {
        this.order = order;
        order.getOrderItems().add(this);
    }*/
    public void addOrder(Order order) {
        this.order = order;
        order.getOrderItems().add(this);
    }

    //=== 생성 메서드 ===//
    @Builder(builderMethodName = "createOrderItemBuilder")
    public OrderItem(Item item, int orderPrice, int count) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
        item.removeStock(count);
    }

    /**
     * 주문상품 추가
     */
    public OrderItem(Order order, Item item, int count) {
        this.order = order;
        this.item = item;
        this.orderPrice = item.getPrice();
        this.count = count;
        item.removeStock(count);
    }

    //===비즈니스 로직===//
    /**
     * 주문상품 취소
     */
    public void cancel() {
        this.getItem().addStock(count);
    }

    //===조회 로직===//
    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return this.getOrderPrice() * this.getCount();
    }

    //===변경 로직===//
    @Builder(builderMethodName = "updateOrderitemBuilder")
    public void updateOrderItem(Integer orderPrice, Integer count) {
        if (orderPrice != null) {
            this.orderPrice = orderPrice;
        }
        if (count != null) {
            item.addStock(this.count);
            item.removeStock(count);
            this.count = count;
        }
    }
}
