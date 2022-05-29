package com.heech.hellcoding.core.shop.item.domain;

import com.heech.hellcoding.core.common.exception.NotEnoghStockException;
import lombok.Builder;
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
@DiscriminatorColumn(name = "dtype")
@Getter
@NoArgsConstructor
public abstract class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    private int price;
    private int stockQuantity;

    //=== 생성메서드 ===//
    public void createItem(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    //===비즈니스 로직===//
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoghStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
