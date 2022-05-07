package com.heech.hellcoding.admin.item.form;

import com.heech.hellcoding.core.item.domain.ItemType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class EditItemForm {

    @NotNull
    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max=1000000)
    private Integer price;

    private Integer quantity;

    private Boolean open; //판매 여부
    private List<String> regionList; //등록지역
    private ItemType itemType; //상품 종류
    private String deliveryCode; //배송 방식
}
