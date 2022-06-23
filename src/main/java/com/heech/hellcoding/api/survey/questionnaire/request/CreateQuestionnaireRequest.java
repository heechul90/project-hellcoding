package com.heech.hellcoding.api.survey.questionnaire.request;

import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateQuestionnaireRequest {

    private String questionnaireTitle;

    private String questionnaireDescription;


    private String periodAt;


    private String beginDate = "20220623000000";
    private String endDate = "20220722235959";

    private List<CreateQuestion> questions;

    @Getter
    @Setter
    public static class CreateQuestion {
        private String questionTitle;
        private Integer questionOrder;
        private Setting setting;
        private List<CreateOption> options;
    }

    @Getter
    @Setter
    public static class CreateOption {
        private Integer optionOrder;
        private String optionContent;
    }


}
