package com.heech.hellcoding.core.shop.orderItem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemDto {

    private Long orderItemId;
    private String itemName;
    private int itemPrice;
    private int orderPrice;
    private int orderCount;

    public OrderItemDto(String itemName, int orderPrice, int orderCount) {
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }
}
