package com.heech.hellcoding.core.shop.item.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonItemDto {

    private String itemName;
    private String itemTitle;
    private String itemContent;
    private int price;
    private int stockQuantity;

}
