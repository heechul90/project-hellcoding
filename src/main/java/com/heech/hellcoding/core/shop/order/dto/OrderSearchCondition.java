package com.heech.hellcoding.core.shop.order.dto;

import com.heech.hellcoding.core.common.dto.CommonSearchCondition;
import com.heech.hellcoding.core.shop.order.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchCondition extends CommonSearchCondition {

    private OrderStatus searchOrderStatus;
}
