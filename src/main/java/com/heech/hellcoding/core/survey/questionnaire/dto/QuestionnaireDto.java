package com.heech.hellcoding.core.survey.questionnaire.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class QuestionnaireDto {

    private Long questionnaireId;
    private String questionnaireTitle;
    private String questionnaireDescription;
    private String isPeriod;
    private String beginDate;
    private String endDate;
    private QuestionnaireStatus questionnaireStatus;

    public QuestionnaireDto(Long questionnaireId, String questionnaireTitle, String questionnaireDescription, String isPeriod, LocalDateTime beginDate, LocalDateTime endDate) {
        this.questionnaireId = questionnaireId;
        this.questionnaireTitle = questionnaireTitle;
        this.questionnaireDescription = questionnaireDescription;
        this.isPeriod = isPeriod;
        if ("Y".equals(isPeriod)) {
            this.beginDate = beginDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.endDate = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            this.beginDate = null;
            this.endDate = null;
        }
        if ("N".equals(isPeriod)) {
            this.questionnaireStatus = QuestionnaireStatus.ST01;
        } else if (beginDate.isBefore(LocalDateTime.now())) {
            this.questionnaireStatus = QuestionnaireStatus.ST01;
        } else if (LocalDateTime.now().isAfter(beginDate) && LocalDateTime.now().isBefore(endDate)) {
            this.questionnaireStatus = QuestionnaireStatus.ST02;
        } else if (endDate.isAfter(LocalDateTime.now())) {
            this.questionnaireStatus = QuestionnaireStatus.ST03;
        }
    }
}
