package com.heech.hellcoding.api.survey.questionnaire.request;

import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class UpdateQuestionnaireRequest {

    @NotEmpty
    private String questionnaireTitle;
    private String questionnaireDescription;
    private String isPeriod;
    @Length(min = 14)
    private String beginDate;
    @Length(min = 14)
    private String endDate;
    private List<UpdateQuestionRequest> questions;

    @Getter
    @Setter
    public static class UpdateQuestionRequest {
        private Long questionId;
        private String questionTitle;
        private Integer questionOrder;
        private Setting setting;
        private List<UpdateOptionRequest> options;
    }

    @Getter
    @Setter
    public static class UpdateOptionRequest {
        private Long optionId;
        private Integer optionOrder;
        private String optionContent;
    }
}
