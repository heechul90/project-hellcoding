package com.heech.hellcoding.core.shop.item.info.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemDto {

    private String itemName;
    private String itemTitle;
    private String itemContent;
    private int price;
    private int stockQuantity;

}
