package com.heech.hellcoding.core.shop.order.domain;

import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.heech.hellcoding.core.shop.orderItem.domain.QOrderItem.orderItem;

@Entity
/*@SequenceGenerator(
        name = "order_seq_generator",
        sequenceName = "order_seq",
        initialValue = 1, allocationSize = 100
)*/
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    //===연관관계 편의 메서드===//
    /*public void addOrderItems(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }*/

    //===생성 메서드===//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .status(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .build();
        //Arrays.stream(orderItems).forEach(order::addOrderItems);
        Arrays.stream(orderItems).forEachOrdered(orderItem -> orderItem.addOrder(order));
        return order;
    }

    //===비즈니스 록직===//
    /**
     * 주문취소
     */
    public void cancel() {
        if (this.delivery.getStatus().equals(DeliveryStatus.COMPLETE)) {
            throw new IllegalStateException("배송 완료된 상품은 취소가 불가능합니다.");
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
