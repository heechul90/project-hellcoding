package com.heech.hellcoding.api.survey.questionnaire.request;

import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateQuestionnaireRequest {

    @NotEmpty
    @Length(max = 120)
    private String questionnaireTitle;
    private String questionnaireDescription;
    private String isPeriod = "N";
    @Length(min = 14)
    private String beginDate;
    @Length(min = 14)
    private String endDate;

    @NotNull @Valid
    private List<CreateQuestion> questions;

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateQuestion {
        @NotEmpty
        private String questionTitle;
        @PositiveOrZero
        private Integer questionOrder;
        private Setting setting;

        @NotNull
        private List<CreateOption> options;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateOption {

        @PositiveOrZero
        private Integer optionOrder;
        private String optionContent;
    }
}
