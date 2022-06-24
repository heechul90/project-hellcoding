package com.heech.hellcoding.core.shop.category.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDto {

    private Long categoryId;
    private String categoryName;
    private int categoryOrder;
    private String isActivate;
    private CategoryDto parentCategory;
    private LocalDateTime createdDate;
    private String createdBy;

    public CategoryDto(Long categoryId, String categoryName, int categoryOrder) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryOrder = categoryOrder;
    }

    public CategoryDto(Long categoryId, String categoryName, int categoryOrder, String isActivate, CategoryDto parentCategory, LocalDateTime createdDate, String createdBy) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryOrder = categoryOrder;
        this.isActivate = isActivate;
        this.parentCategory = parentCategory;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }
}
