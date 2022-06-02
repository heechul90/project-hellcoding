package com.heech.hellcoding.core.shop.item.info.dto;

import com.heech.hellcoding.core.common.dto.CommonSearchCondition;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemSearchCondition extends CommonSearchCondition {

    private Integer searchPriceGoe;
    private Integer searchPriceLoe;

    private List<Long> searchIds = new ArrayList<>();
}
