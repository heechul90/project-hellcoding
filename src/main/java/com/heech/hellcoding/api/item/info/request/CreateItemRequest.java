package com.heech.hellcoding.api.item.info.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateItemRequest {

    @NotEmpty
    @Size(max = 60)
    private String itemName;

    @Size(max = 255)
    private String itemTitle;

    @Size(max = 2000)
    private String itemContent;

    @PositiveOrZero
    private Integer price;

    @PositiveOrZero
    private Integer stockQuantity;

}
