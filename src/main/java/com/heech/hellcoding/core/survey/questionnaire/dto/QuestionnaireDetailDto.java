package com.heech.hellcoding.core.survey.questionnaire.dto;

import com.heech.hellcoding.core.survey.question.dto.QuestionDetailDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class QuestionnaireDetailDto {

    private Long questionnaireId;
    private String questionnaireTitle;
    private String questionnaireDescription;
    private String periodAt;
    private String beginDate;
    private String endDate;
    private QuestionnaireStatus questionnaireStatus;

    private List<QuestionDetailDto> questions;

    public QuestionnaireDetailDto(Long questionnaireId, String questionnaireTitle, String questionnaireDescription, String periodAt, LocalDateTime beginDate, LocalDateTime endDate, List<QuestionDetailDto> questions) {
        this.questionnaireId = questionnaireId;
        this.questionnaireTitle = questionnaireTitle;
        this.questionnaireDescription = questionnaireDescription;
        this.periodAt = periodAt;
        if ("Y".equals(periodAt)) {
            this.beginDate = beginDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.endDate = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            this.beginDate = null;
            this.endDate = null;
        }
        if ("N".equals(periodAt)) {
            this.questionnaireStatus = QuestionnaireStatus.ST01;
        } else if (beginDate.isBefore(LocalDateTime.now())) {
            this.questionnaireStatus = QuestionnaireStatus.ST01;
        } else if (LocalDateTime.now().isAfter(beginDate) && LocalDateTime.now().isBefore(endDate)) {
            this.questionnaireStatus = QuestionnaireStatus.ST02;
        } else if (endDate.isAfter(LocalDateTime.now())) {
            this.questionnaireStatus = QuestionnaireStatus.ST03;
        }
        this.questions = questions;
    }

}
