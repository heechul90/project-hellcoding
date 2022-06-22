package com.heech.hellcoding.api.shop.category.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class CreateCategoryRequest {

    private Long parentId;

    @NotEmpty
    private String categoryName;

    @NotNull
    @PositiveOrZero
    private Integer categoryOrder;
}
