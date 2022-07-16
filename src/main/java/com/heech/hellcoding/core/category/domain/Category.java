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

import static org.springframework.util.StringUtils.hasText;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ServiceName serviceName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

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
    public Category(Category parent, ServiceName serviceName, Integer serialNumber, String name, String content) {
        Assert.notNull(serviceName, "serviceName 필수.");
        Assert.notNull(serialNumber, "serialNumber 필수.");
        Assert.hasText(name, "name 필수.");

        this.serviceName = serviceName;
        this.serialNumber = serialNumber;
        this.name = name;
        this.content = content;

        if (parent != null) {
            this.parent = parent;
            this.parent.getChildren().add(this);
        }
    }

    //===수정 메서드===//
    @Builder(builderClassName = "updateCategoryBuilder", builderMethodName = "updateCategoryBuilder")
    public void updateCategory(Category parent, ServiceName serviceName, Integer serialNumber, String name, String content) {
        if (serviceName != null) this.serviceName = serviceName;
        if (serialNumber != null) this.serialNumber = serialNumber;
        if (hasText(name)) this.name = name;
        if (hasText(content)) this.content = content;
        if (parent != null) {
            this.parent = parent;
            this.parent.getChildren().add(this);
        }
    }
}
