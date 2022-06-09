package com.heech.hellcoding.api.shop.orderItem.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class CreateOrderItemRequest {

    @NotNull
    private Long itemId;

    //@NotNull
    //@PositiveOrZero
    private Integer orderPrice;

    @NotNull
    @Positive
    private Integer count;
}
