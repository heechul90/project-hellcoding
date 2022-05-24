package com.heech.hellcoding.core.temp.domain;

public enum DeliveryCode {

    FAST("빠른배송"), NORMAL("일반배송"), SLOW("느린배송");

    private final String description;

    DeliveryCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
