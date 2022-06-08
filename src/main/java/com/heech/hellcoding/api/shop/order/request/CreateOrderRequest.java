package com.heech.hellcoding.api.shop.order.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    @Positive
    private Long memberId;

    @NotEmpty
    private List<ItemInfo> itemInfos = new ArrayList<>();
}
