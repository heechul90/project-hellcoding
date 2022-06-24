package com.heech.hellcoding.core.survey.questionnaire.dto;

import com.heech.hellcoding.core.survey.question.dto.QuestionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateUpdateQuestionnaireDto {

    private String questionnaireTitle;
    private String questionnaireDescription;
    private String periodAt;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    private List<QuestionDto> questions;

    public CreateUpdateQuestionnaireDto(String questionnaireTitle, String questionnaireDescription, String periodAt, LocalDateTime beginDate, LocalDateTime endDate) {
        this.questionnaireTitle = questionnaireTitle;
        this.questionnaireDescription = questionnaireDescription;
        this.periodAt = periodAt;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public CreateUpdateQuestionnaireDto(String questionnaireTitle, String questionnaireDescription, String periodAt, LocalDateTime beginDate, LocalDateTime endDate, List<QuestionDto> questions) {
        this.questionnaireTitle = questionnaireTitle;
        this.questionnaireDescription = questionnaireDescription;
        this.periodAt = periodAt;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.questions = questions;
    }
}
