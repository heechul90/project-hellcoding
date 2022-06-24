package com.heech.hellcoding.core.survey.option.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOptionParam {

    private Long optionId;
    private Integer optionOrder;
    private String optionContent;

    /**
     * 옵션 수정(option_id 없으면 null)
     */
    public UpdateOptionParam(Long optionId, Integer optionOrder, String optionContent) {
        this.optionId = optionId;
        this.optionOrder = optionOrder;
        this.optionContent = optionContent;
    }
}
