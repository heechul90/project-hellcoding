package com.heech.hellcoding.api.order.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class ItemInfo {

    @NotEmpty
    private Long itemId;

    @NotEmpty
    private int orderCount;
}
