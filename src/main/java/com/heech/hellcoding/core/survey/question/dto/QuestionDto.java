package com.heech.hellcoding.core.survey.question.dto;

import com.heech.hellcoding.core.survey.option.dto.OptionDto;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDto {

    private Long questionId;
    private String questionTitle;
    private Integer questionOrder;
    private Setting setting;
    private List<OptionDto> options;

    public QuestionDto(String questionTitle, Integer questionOrder, Setting setting, List<OptionDto> options) {
        this.questionTitle = questionTitle;
        this.questionOrder = questionOrder;
        this.setting = setting;
        this.options = options;
    }

    public QuestionDto(Long questionId, String questionTitle, Integer questionOrder, Setting setting, List<OptionDto> options) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionOrder = questionOrder;
        this.setting = setting;
        this.options = options;
    }
}
