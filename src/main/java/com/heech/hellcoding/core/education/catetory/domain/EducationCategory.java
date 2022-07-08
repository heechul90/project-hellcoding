package com.heech.hellcoding.core.education.catetory.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "education_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EducationCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private EducationCategory parent;

    @Column(name = "education_category_name")
    private String name;

    private int categoryOrder;
    private boolean isActivate;

    //===생성 메서드===//
    /**
     * 카테고리 생성
     */
    @Builder(builderClassName = "createEducationCategoryBuilder", builderMethodName = "createEducationCategoryBuilder")
    public EducationCategory(EducationCategory parent, String name, int categoryOrder) {
        this.parent = parent;
        this.name = name;
        this.categoryOrder = categoryOrder;
        this.isActivate = true;
    }
}
