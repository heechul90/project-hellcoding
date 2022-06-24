package com.heech.hellcoding.core.survey.question.dto;

import com.heech.hellcoding.core.survey.option.dto.UpdateOptionParam;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateQuestionParam {

    private Long questionId;
    private String questionTitle;
    private Integer questionOrder;
    private Setting setting;
    private List<UpdateOptionParam> options;

    /**
     * 질문 수정(question_id 없으면 null)
     */
    @Builder(builderClassName = "updateQuestionBuilder", builderMethodName = "updateQuestionBuilder")
    public UpdateQuestionParam(Long questionId, String questionTitle, Integer questionOrder, Setting setting, List<UpdateOptionParam> options) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionOrder = questionOrder;
        this.setting = setting;
        this.options = options;
    }
}
