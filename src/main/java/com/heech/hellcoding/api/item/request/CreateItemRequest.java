package com.heech.hellcoding.api.item.request;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
public class CreateItemRequest {

    private String itemName;
    private String itemTitle;
    private String itemContent;
    private int price;
    private int stockQuantity;
}
