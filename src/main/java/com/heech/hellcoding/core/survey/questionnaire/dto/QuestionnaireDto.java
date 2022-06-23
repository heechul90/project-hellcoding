package com.heech.hellcoding.core.survey.questionnaire.dto;

import com.heech.hellcoding.core.survey.question.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionnaireDto {

    private String questionnaireTitle;
    private String questionnaireDescription;
    private String periodAt;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    private List<QuestionDto> questions;

    public QuestionnaireDto(String questionnaireTitle, String questionnaireDescription, String periodAt, LocalDateTime beginDate, LocalDateTime endDate) {
        this.questionnaireTitle = questionnaireTitle;
        this.questionnaireDescription = questionnaireDescription;
        this.periodAt = periodAt;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }
}
