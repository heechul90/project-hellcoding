package com.heech.hellcoding.core.common.json;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Error {

    private String fieldName;
    private String errorMessage;

    @Builder
    public Error(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }
}
