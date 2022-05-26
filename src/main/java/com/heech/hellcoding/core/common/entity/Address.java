package com.heech.hellcoding.core.common.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    /** 우편번호 */
    @Column(length = 5)
    private String zipcode;
    /** 주소 */
    private String address;
    /** 상세주소 */
    private String detailAddress;

    public Address(String zipcode, String address, String detailAddress) {
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
    }

    //===변환 메서드===//
    public String fullAddress() {
        return "(" + this.zipcode + ") " + this.address + " " + this.detailAddress;
    }
}
