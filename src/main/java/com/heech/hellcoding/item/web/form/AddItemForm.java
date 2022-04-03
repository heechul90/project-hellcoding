package com.heech.hellcoding.item.web.form;

import com.heech.hellcoding.item.domain.ItemType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AddItemForm {

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(value = 9999)
    private Integer quantity;

    private Boolean open; //판매 여부
    private List<String> regionList; //등록지역
    private ItemType itemType; //상품 종류
    private String deliveryCode; //배송 방식
}
