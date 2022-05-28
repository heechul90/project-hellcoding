package com.heech.hellcoding.api.order.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class CreateOrderRequest {

    private Long memberId;
    private Long itemId;
    private int orderCount;
}
