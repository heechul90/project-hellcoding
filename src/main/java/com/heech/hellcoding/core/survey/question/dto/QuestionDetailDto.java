package com.heech.hellcoding.core.survey.question.dto;

import com.heech.hellcoding.core.survey.option.dto.OptionDetailDto;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDetailDto {

    private Long questionId;
    private String questionTitle;
    private int questionOrder;
    private Setting settingCode;
    private String setting;

    private List<OptionDetailDto> options;

    public QuestionDetailDto(Long questionId, String questionTitle, int questionOrder, Setting setting, List<OptionDetailDto> options) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionOrder = questionOrder;
        this.settingCode = setting;
        if (Setting.OBJECTIVE.equals(setting)) {
            this.setting = "객관식";
        } else
            this.setting = "주관식";
        this.options = options;
    }
}
