package com.heech.hellcoding.api.shop.delivery.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UpdateDeliveryRequest {

    @Length(max = 5)
    @NotEmpty
    private String zipcode;

    @Length(max = 255)
    @NotEmpty
    private String address;

    @Length(max = 255)
    @NotEmpty
    private String detailAddress;
}
