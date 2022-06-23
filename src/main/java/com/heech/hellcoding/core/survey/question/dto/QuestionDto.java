package com.heech.hellcoding.core.survey.question.dto;

import com.heech.hellcoding.core.survey.option.dto.OptionDto;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionDto {

    private String questionTitle;
    private Integer questionOrder;
    private Setting setting;
    private List<OptionDto> options;

}
