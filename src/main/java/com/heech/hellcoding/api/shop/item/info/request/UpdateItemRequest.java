package com.heech.hellcoding.api.shop.item.info.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateItemRequest {

    @Size(max = 60)
    private String itemName;

    @Size(max = 255)
    private String itemTitle;

    @Size(max = 2000)
    private String itemContent;

    @PositiveOrZero
    private Integer price;

    @PositiveOrZero
    private Integer stockQuantity;

    @PositiveOrZero
    private Long categoryId;

}
