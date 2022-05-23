package com.heech.hellcoding.core.delivery.domain;

import com.heech.hellcoding.core.common.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "delivery_seq_generator",
        sequenceName = "delivery_seq",
        initialValue = 1, allocationSize = 100
)
@Getter
@NoArgsConstructor
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_seq_generator")
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    //===생성 메서드===//

    public Delivery(Address address, DeliveryStatus status) {
        this.address = address;
        this.status = status;
    }
}
