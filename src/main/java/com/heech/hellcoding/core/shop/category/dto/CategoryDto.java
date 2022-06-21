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
    private String activation;
    private CategoryDto parentCategory;
    private LocalDateTime createdDate;
    private String createdBy;

    public CategoryDto(Long categoryId, String categoryName, int categoryOrder) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryOrder = categoryOrder;
    }

    public CategoryDto(Long categoryId, String categoryName, int categoryOrder, String activation, CategoryDto parentCategory, LocalDateTime createdDate, String createdBy) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryOrder = categoryOrder;
        this.activation = activation;
        this.parentCategory = parentCategory;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
    }
}
