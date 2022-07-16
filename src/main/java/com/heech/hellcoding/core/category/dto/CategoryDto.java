package com.heech.hellcoding.core.category.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDto {

    private Long categoryId;
    private String serviceName;
    private String categoryName;
    private String categoryContent;
    private int categorySerialNumber;
    private CategoryDto parentCategory;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public CategoryDto(Long categoryId, String categoryName, int categorySerialNumber) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categorySerialNumber = categorySerialNumber;
    }

    public CategoryDto(Long categoryId, String serviceName, String categoryName, String categoryContent, int categorySerialNumber, CategoryDto parentCategory, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.categoryId = categoryId;
        this.serviceName = serviceName;
        this.categoryName = categoryName;
        this.categoryContent = categoryContent;
        this.categorySerialNumber = categorySerialNumber;
        this.parentCategory = parentCategory;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
