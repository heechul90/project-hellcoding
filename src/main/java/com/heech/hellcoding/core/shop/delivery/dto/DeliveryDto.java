package com.heech.hellcoding.core.shop.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryDto {

    private Long deliveryId;
    private String deliveryStatus;
    private String deliveryAddress;

}
