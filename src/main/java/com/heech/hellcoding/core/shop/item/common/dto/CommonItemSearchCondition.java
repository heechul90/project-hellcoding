package com.heech.hellcoding.core.shop.item.common.dto;

import com.heech.hellcoding.core.common.dto.CommonSearchCondition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonItemSearchCondition extends CommonSearchCondition {

    private Integer searchPriceGoe;
    private Integer searchPriceLoe;
}
