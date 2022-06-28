package com.heech.hellcoding.api.survey.result.request;

import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CreateQuestionnaireResultRequest {

    @NotNull
    private Long memberId;

    @NotNull
    @Valid
    private List<QuestionnaireResult> questionnaireResults;

    @Getter
    @Setter
    public static class QuestionnaireResult {
        @NotNull
        private Setting setting;
        @NotNull
        private Long optionId;
        private String responseContent;
    }

}
