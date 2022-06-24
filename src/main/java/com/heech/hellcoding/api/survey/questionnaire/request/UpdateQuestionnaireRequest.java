package com.heech.hellcoding.api.survey.questionnaire.request;

import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateQuestionnaireRequest {

    private String questionnaireTitle;
    private String questionnaireDescription;
    private String isPeriod;
    private String beginDate;
    private String endDate;
    private List<UpdateQuestion> questions;

    @Getter
    @Setter
    public static class UpdateQuestion {
        private Long questionId;
        private String questionTitle;
        private Integer questionOrder;
        private Setting setting;
        private List<UpdateOption> options;
    }

    @Getter
    @Setter
    public static class UpdateOption {
        private Long optionId;
        private Integer optionOrder;
        private String optionContent;
    }
}
