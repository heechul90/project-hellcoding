package com.heech.hellcoding.core.category.domain;

import com.heech.hellcoding.core.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Enumerated(EnumType.STRING)
    private ServiceSection serviceSection;

    @Column(name = "category_serial_number")
    private int serialNumber;
    @Column(name = "category_name")
    private String name;
    @Column(name = "category_content")
    private String content;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();

    //===생성 메서드===//
    /**
     * 카테고리 생성
     */
    @Builder(builderClassName = "createCategoryBuilder", builderMethodName = "createCategoryBuilder")
    public Category(Category parent, ServiceSection serviceSection, Integer serialNumber, String name, String content) {
        Assert.notNull(serviceSection, "serviceSection 필수.");
        Assert.notNull(serialNumber, "serialNumber 필수.");
        Assert.hasText(name, "name 필수.");

        this.parent = parent;
        this.serviceSection = serviceSection;
        this.serialNumber = serialNumber;
        this.name = name;
        this.content = content;
    }

    //===수정 메서드===//
    public void updateCategory(Category parent, ServiceSection serviceSection, Integer serialNumber, String name, String content) {

    }
}
