package com.heech.hellcoding.core.shop.orderItem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemSearchCondition {

    private Long searchOrderId;
    private Integer searchOrderPriceGoe;
    private Integer searchOrderPriceLoe;
}
