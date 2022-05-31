package com.heech.hellcoding.core.shop.item.common.domain;

import com.heech.hellcoding.core.common.exception.NotEnoghStockException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import static org.springframework.util.StringUtils.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    @Column(name = "item_title")
    private String title;

    @Column(name = "item_content")
    private String content;

    private int price;
    private int stockQuantity;

    //=== 생성메서드 ===//
    public Item(String name, String title, String content, int price, int stockQuantity) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    //===변경 메서드===//
    protected void updateItem(String name, String title, String content, int price, int stockQuantity) {
        if (hasText(name)) {
            this.name = name;
        }
        if (hasText(title)) {
            this.title = title;
        }
        if (hasText(content)) {
            this.content = content;
        }
        if (price >= 0) {
            this.price = price;
        }
        if (stockQuantity >= 0) {
            this.stockQuantity = stockQuantity;
        }
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
