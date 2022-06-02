package com.heech.hellcoding.api.order.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    @NotEmpty
    private Long memberId;
    private List<ItemInfo> itemInfos = new ArrayList<>();
}
