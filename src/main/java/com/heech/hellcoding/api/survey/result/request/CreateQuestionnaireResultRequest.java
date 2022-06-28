package com.heech.hellcoding.api.survey.result.request;

import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CreateQuestionnaireResultRequest {

    private Long memberId;

    @NotNull
    @Valid
    private List<QuestionnaireResult> questionnaireResults;

    private static class QuestionnaireResult {
        @NotEmpty
        private Setting setting;
        private String responseContent;
        private Long optionId;
    }

}
