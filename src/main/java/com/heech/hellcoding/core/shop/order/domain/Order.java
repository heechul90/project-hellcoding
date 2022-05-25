package com.heech.hellcoding.core.shop.order.domain;

import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
/*@SequenceGenerator(
        name = "order_seq_generator",
        sequenceName = "order_seq",
        initialValue = 1, allocationSize = 100
)*/
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //===생성 메서드===//
    public Order(LocalDateTime orderDate, OrderStatus status, Member member, Delivery delivery) {
        this.orderDate = orderDate;
        this.status = status;
        this.member = member;
        this.delivery = delivery;
    }
}
