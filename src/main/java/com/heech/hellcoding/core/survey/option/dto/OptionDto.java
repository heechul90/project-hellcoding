package com.heech.hellcoding.core.survey.option.dto;

import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OptionDto {

    private Long optionId;
    private Integer optionOrder;
    private String optionContent;

    public OptionDto(Integer optionOrder, String optionContent) {
        this.optionOrder = optionOrder;
        this.optionContent = optionContent;
    }

    public OptionDto(Long optionId, Integer optionOrder, String optionContent) {
        this.optionId = optionId;
        this.optionOrder = optionOrder;
        this.optionContent = optionContent;
    }
}
