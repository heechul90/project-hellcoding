package com.heech.hellcoding.core.shop.category.domain;

import com.heech.hellcoding.core.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(name = "category_name")
    private String name;

    private int categoryOrder;
    private String activation;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    //===생성 메서드===//
    /**
     * 카테고리 생성
     */
    @Builder(builderClassName = "createRootCategoryBuilder", builderMethodName = "createRootCategoryBuilder")
    public Category(String name, Integer categoryOrder) {
        Assert.hasText(name, "category_name 필수입니다.");
        Assert.notNull(categoryOrder, "category_order 필수입니다.");

        this.name = name;
        this.categoryOrder = categoryOrder;
        this.activation = "Y";
    }

    /**
     * 하위카테고리 생성
     */
    @Builder(builderClassName = "createChildCategoryBuilder", builderMethodName = "createChildCategoryBuilder")
    public Category(Category parent, String name, Integer categoryOrder) {
        Assert.notNull(parent, "parent 필수입니다.");
        Assert.hasText(name, "category_name 필수입니다.");
        Assert.notNull(categoryOrder, "category_order 필수입니다.");

        this.parent = parent;
        this.name = name;
        this.categoryOrder = categoryOrder;
        this.activation = "Y";
        this.parent.getChildren().add(this);
    }

    //===수정 메서드===//
    /**
     * 카테고리 수정
     */
    @Builder(builderClassName = "updateCategoryBuilder", builderMethodName = "updateCategoryBuilder")
    public void updateCategory(String name, Integer categoryOrder) {
        if (hasText(name)) this.name = name;
        if (categoryOrder != null) this.categoryOrder = categoryOrder;
    }

    /**
     * 카테고리 활성화
     */
    public void activateCategory() {
        this.activation = "Y";
    }

    /**
     * 카테고리 비활성화
     */
    public void deactivateCategory() {
        this.activation = "N";
    }

    //===비즈니스 로직===//
}
