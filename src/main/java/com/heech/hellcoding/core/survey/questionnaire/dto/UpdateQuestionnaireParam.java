package com.heech.hellcoding.core.survey.questionnaire.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateQuestionnaireParam {

    private String title;
    private String description;
    private String periodAt;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;

}
