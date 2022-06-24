package com.heech.hellcoding.core.survey.questionnaire.dto;

import com.heech.hellcoding.core.survey.question.dto.UpdateQuestionParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateQuestionnaireParam {

    private String questionnaireTitle;
    private String questionnaireDescription;
    private String isPeriod;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    private List<UpdateQuestionParam> questions;

    /**
     * 설문 수정
     */
    public UpdateQuestionnaireParam(String questionnaireTitle, String questionnaireDescription, String isPeriod, LocalDateTime beginDate, LocalDateTime endDate, List<UpdateQuestionParam> questions) {
        this.questionnaireTitle = questionnaireTitle;
        this.questionnaireDescription = questionnaireDescription;
        this.isPeriod = isPeriod;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.questions = questions;
    }




}
