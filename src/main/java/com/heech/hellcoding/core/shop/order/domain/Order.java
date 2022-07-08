package com.heech.hellcoding.core.shop.order.domain;

import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
/*@SequenceGenerator(
        name = "order_seq_generator",
        sequenceName = "order_seq",
        initialValue = 1, allocationSize = 100
)*/
@Table(name = "shop_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    //===생성 메서드===//
    @Builder(builderMethodName = "createBuilder")
    public Order(Member member, Delivery delivery, OrderItem... orderItems) {
        this.member = member;
        this.delivery = delivery;
        this.status = OrderStatus.ORDER;
        this.orderDate = LocalDateTime.now();
        Arrays.stream(orderItems).forEachOrdered(orderItem -> orderItem.addOrder(this));
    }

    @Builder(builderMethodName = "createBuilderTest")
    public Order(Member member, Delivery delivery, List<OrderItem> orderItems) {
        this.member = member;
        this.delivery = delivery;
        this.status = OrderStatus.ORDER;
        this.orderDate = LocalDateTime.now();
        orderItems.forEach(orderItem -> orderItem.addOrder(this));
        //Arrays.stream(orderItems).forEachOrdered(orderItem -> orderItem.addOrder(this));
    }

    //===비즈니스 록직===//
    /**
     * 주문취소
     */
    public void cancel() {
        if (this.delivery.getStatus().equals(DeliveryStatus.COMPLETE)) {
            throw new IllegalStateException("배송 완료된 상품은 취소가 불가능합니다.");
        } else if (this.delivery.getStatus().equals(DeliveryStatus.DELIVERY)) {
            throw new IllegalStateException("배송중인 상품은 취소가 불가능합니다.");
        }
        this.status = OrderStatus.CANCEL;
        this.orderItems.forEach(OrderItem::cancel);
    }

    //===조회 로직===//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}
