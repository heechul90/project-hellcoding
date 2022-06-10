package com.heech.hellcoding.core.shop.delivery.dto;

import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliverySearchCondition {

    private DeliveryStatus searchDeliveryStatus;

}
