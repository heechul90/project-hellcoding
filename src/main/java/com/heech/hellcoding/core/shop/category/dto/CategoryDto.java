package com.heech.hellcoding.core.shop.category.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "categoryId")
public class CategoryDto {

    private Long categoryId;
    private String categoryName;
    private String categoryTitle;
    private String categoryContent;
    private List<CategoryDto> childCategories = new ArrayList<>();

    public CategoryDto(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public CategoryDto(Long categoryId, String categoryName, List<CategoryDto> childCategories) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.childCategories = childCategories;
    }

    public CategoryDto(Long categoryId, String categoryName, String categoryTitle, String categoryContent) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryTitle = categoryTitle;
        this.categoryContent = categoryContent;
    }

    public CategoryDto(Long categoryId, String categoryName, String categoryTitle, String categoryContent, List<CategoryDto> childCategories) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryTitle = categoryTitle;
        this.categoryContent = categoryContent;
        this.childCategories = childCategories;
    }
}