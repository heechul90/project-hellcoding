package com.heech.hellcoding.core.survey.questionnaire.domain;

import com.heech.hellcoding.core.common.entity.BaseEntity;
import com.heech.hellcoding.core.survey.questionnaire.dto.UpdateQuestionnaireParam;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.springframework.util.StringUtils.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Questionnaire extends BaseEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionnaire_id")
    private Long id;

    @Column(name = "questionnaire_title")
    private String title;

    private String description;

    @Column(columnDefinition = "char")
    private String periodAt; //Y,N

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    @Column(columnDefinition = "char")
    private String useAt; //Y, N

    //===생성 메서드===//
    @Builder(builderClassName = "createQuestionnaireBuilder", builderMethodName = "createQuestionnaireBuilder")
    public Questionnaire(String title, String description, String periodAt, LocalDateTime beginDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.periodAt = periodAt;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.useAt = "Y";
    }

    //===수정 메서드===//
    @Builder(builderClassName = "updateQuestionnaireBuilder", builderMethodName = "updateQuestionnaireBuilder")
    public void updateQuestionnaire(UpdateQuestionnaireParam param) {
        if (hasText(param.getTitle())) this.title = param.getTitle();
        if (hasText(param.getDescription())) this.description = param.getDescription();
        if (hasText(param.getPeriodAt())) this.periodAt = param.getPeriodAt();
        if ("Y".equals(param.getPeriodAt())) {
            this.beginDate = param.getBeginDate();
            this.endDate = param.getEndDate();
        } else {
            this.beginDate = null;
            this.endDate = null;
        }
    }

}
