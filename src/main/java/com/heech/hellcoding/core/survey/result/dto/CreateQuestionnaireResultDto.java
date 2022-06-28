package com.heech.hellcoding.core.survey.result.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateQuestionnaireResultDto {

    private Long memberId;
    private Long optionId;
    private String responseContent;

}
