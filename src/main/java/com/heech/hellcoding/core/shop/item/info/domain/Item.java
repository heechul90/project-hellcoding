package com.heech.hellcoding.core.shop.item.info.domain;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.common.entity.BaseEntity;
import com.heech.hellcoding.core.common.exception.NotEnoghStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static org.springframework.util.StringUtils.*;

@Entity
@Table(name = "shop_item")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_item_id")
    private Long id;

    @Column(name = "shop_item_name")
    private String name;

    @Column(name = "shop_item_title")
    private String title;

    @Column(name = "shop_item_content")
    private String content;

    private int price;
    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    //=== 생성메서드 ===//
    protected Item(String name, String title, String content, int price, int stockQuantity, Category category) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    //===변경 메서드===//
    protected void updateItem(String name, String title, String content, int price, int stockQuantity, Category category) {
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
        if (category != null) {
            this.category = category;
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
