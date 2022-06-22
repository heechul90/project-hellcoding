package com.heech.hellcoding.api.shop.category.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class UpdateCategoryRequest {

    private String categoryName;

    @PositiveOrZero
    private Integer categoryOrder;
}
