package com.heech.hellcoding.core.shop.orderItem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemDto {

    private String itemName;
    private int orderPrice;
    private int count;
}
