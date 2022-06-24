package com.heech.hellcoding.core.survey.questionnaire.domain;

import com.heech.hellcoding.core.common.entity.BaseEntity;
import com.heech.hellcoding.core.survey.question.domain.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private String isPeriod; //Y,N

    private LocalDateTime beginDate;

    private LocalDateTime endDate;

    @Column(columnDefinition = "char")
    private String isDelete; //Y, N

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    //===생성 메서드===//
    /**
     * 설문 생성
     */
    @Builder(builderClassName = "createQuestionnaireBuilder", builderMethodName = "createQuestionnaireBuilder")
    public Questionnaire(String title, String description, String isPeriod, LocalDateTime beginDate, LocalDateTime endDate, List<Question> questions) {
        this.title = title;
        this.description = description;
        this.isPeriod = isPeriod;
        if ("Y".equals(isPeriod)) {
            this.beginDate = beginDate;
            this.endDate = endDate;
        } else {
            this.beginDate = null;
            this.endDate = null;
        }
        this.isDelete = "N";
        if (questions != null) {
            this.questions = questions;
            questions.forEach(question -> question.addQuestionnaire(this));
        }
    }

    //===수정 메서드===//
    /**
     * 설문 수정
     */
    @Builder(builderClassName = "updateQuestionnaireBuilder", builderMethodName = "updateQuestionnaireBuilder")
    public void updateQuestionnaire(String title, String description, String isPeriod, LocalDateTime beginDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.isPeriod = isPeriod;
        if ("Y".equals(isPeriod)) {
            this.beginDate = beginDate;
            this.endDate = endDate;
        } else {
            this.beginDate = null;
            this.endDate = null;
        }

    }

    //=== 삭제 로직===//
    /**
     * 설문 삭제(isDelete Y으로 업데이트)
     */
    public void deleteQuestionnaire() {
        this.isDelete = "Y";
    }

}
