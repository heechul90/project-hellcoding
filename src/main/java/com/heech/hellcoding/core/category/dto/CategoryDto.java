package com.heech.hellcoding.core.category.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryDto {

    private Long categoryId;
    private String serviceName;
    private String categoryName;
    private String categoryContent;
    private int categorySerialNumber;
    private List<CategoryDto> childCategories = new ArrayList<>();
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    public CategoryDto(Long categoryId, String serviceName, String categoryName, String categoryContent, int categorySerialNumber, List<CategoryDto> childCategories, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.categoryId = categoryId;
        this.serviceName = serviceName;
        this.categoryName = categoryName;
        this.categoryContent = categoryContent;
        this.categorySerialNumber = categorySerialNumber;
        this.childCategories = childCategories;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
