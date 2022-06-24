package com.heech.hellcoding.core.survey.option.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionDetailDto {

    private Long optionId;
    private int optionOrder;
    private String content;

    public OptionDetailDto(Long optionId, int optionOrder, String content) {
        this.optionId = optionId;
        this.optionOrder = optionOrder;
        this.content = content;
    }
}
