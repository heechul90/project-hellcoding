package com.heech.hellcoding.core.shop.item.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
/*@SequenceGenerator(
        name = "item_seq_generator",
        sequenceName = "seq",
        initialValue = 1, allocationSize = 100
)*/
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    private int price;
    private int quantity;

    //=== 생성메서드 ===//
    public Item(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    //=== 변경메서드 ===//
    public void changeItem(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
