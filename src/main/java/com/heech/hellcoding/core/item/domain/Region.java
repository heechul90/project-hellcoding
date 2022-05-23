package com.heech.hellcoding.core.item.domain;

public enum Region {

    SEOUL("서울"), BUSAN("부산"), JEJU("제주"), DAEJEON("대전"), SEJONG("세종");

    private final String description;

    Region(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
