package com.heech.hellcoding.core.survey.question.domain;

import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questionnaire_question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaire_id")
    private Questionnaire questionnaire;

    @Column(name = "question_title")
    private String title;

    private int questionOrder;

    @Enumerated(EnumType.STRING)
    private Setting setting;

    @OneToMany(mappedBy = "question")
    private List<Option> options = new ArrayList<>();

    //===연관관계 메서드===//
    public void addQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    //===생성 메서드===//
    @Builder(builderClassName = "createQuestionBuilder", builderMethodName = "createQuestionBuilder")
    public Question(String title, int questionOrder, Setting setting, List<Option> options) {
        this.title = title;
        this.questionOrder = questionOrder;
        this.setting = setting;
        this.options = options;
        options.forEach(option -> option.addQuestion(this));
    }

    //===수정 메서드===//
    @Builder(builderClassName = "updateQuestionBuilder", builderMethodName = "updateQuestionBuilder")
    public void updateQuestion(Questionnaire questionnaire, String title, int questionOrder, Setting setting) {
        this.questionnaire = questionnaire;
        this.title = title;
        this.questionOrder = questionOrder;
        this.setting = setting;
    }
}
