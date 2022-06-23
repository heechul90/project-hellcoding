package com.heech.hellcoding.core.survey.option.dto;

import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OptionDto {

    private Integer optionOrder;
    private String optionContent;
}
