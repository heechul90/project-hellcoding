package com.heech.hellcoding.api.order.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
public class ItemInfo {

    @Positive
    private Long itemId;

    @PositiveOrZero
    private int orderCount;
}
