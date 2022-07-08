package com.heech.hellcoding.core.shop.delivery.domain;

import com.heech.hellcoding.core.common.entity.Address;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "shop_delivery")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    //===생성 메서드===//
    /**
     * 저장
     */
    @Builder(builderMethodName = "createDeliveryBuilder")
    public Delivery(Address address) {
        this.address = address;
        this.status = DeliveryStatus.READY;
    }

    /**
     * 수정
     */
    @Builder(builderMethodName = "updateDeliveryBuilder")
    public void updateDelivery(Address address) {
        this.address = address;
    }

    //===비즈니스 로직===//
    /**
     * 배달시작
     */
    public void delivery() {
        this.status = DeliveryStatus.DELIVERY;
    }

    /**
     * 배달완료
     */
    public void complete() {
        this.status = DeliveryStatus.COMPLETE;
    }
}
