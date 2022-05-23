package com.heech.hellcoding.core.item.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "item_seq_generator",
        sequenceName = "item_seq",
        initialValue = 1, allocationSize = 100
)
@Getter
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq_generator")
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    private int price;
    private int quantity;

    private Boolean open; //판매 여부

    @Enumerated(EnumType.STRING)
    private Region region; //등록지역

    @Enumerated(EnumType.STRING)
    private ItemType itemType; //상품 종류

    @Enumerated(EnumType.STRING)
    private DeliveryCode deliveryCode; //배송 방식

    //=== 생성메서드 ===//
    public Item(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Item(String name, int price, int quantity, Boolean open, Region region, ItemType itemType, DeliveryCode deliveryCode) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.open = open;
        this.region = region;
        this.itemType = itemType;
        this.deliveryCode = deliveryCode;
    }
}
