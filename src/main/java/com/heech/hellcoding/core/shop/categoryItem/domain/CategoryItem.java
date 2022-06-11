package com.heech.hellcoding.core.shop.categoryItem.domain;

import com.heech.hellcoding.core.shop.category.domain.Category;
import com.heech.hellcoding.core.shop.item.info.domain.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    //===생성 메서드===//
    @Builder(builderMethodName = "createCategoryItemBuilder")
    public CategoryItem(Item item, Category category) {
        this.item = item;
        this.category = category;
    }
}
