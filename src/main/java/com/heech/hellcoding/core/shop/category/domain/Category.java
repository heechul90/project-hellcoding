package com.heech.hellcoding.core.shop.category.domain;

import com.mysema.commons.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "category_title")
    private String title;

    @Column(name = "category_content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //===생성 메서드===//
    /**
     * 생성
     */
    @Builder(builderMethodName = "createCategoryBuilder")
    public Category(String name, String title, String content) {
        this.name = name;
        this.title = title;
        this.content = content;
    }

    /**
     * child생성
     */
    public Category(String name, String title, String content, Category parent) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.parent = parent;
        this.parent.getChild().add(this);
    }

    //===업데이트 로직===//
    @Builder(builderMethodName = "updateCategoryBuilder")
    public void updateCategory(String name, String title, String content) {
        if (hasText(name)) this.name = name;
        if (hasText(title)) this.title = title;
        if (hasText(content)) this.content = content;
    }
}