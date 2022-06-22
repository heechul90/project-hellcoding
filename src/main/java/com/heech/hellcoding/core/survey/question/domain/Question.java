package com.heech.hellcoding.core.survey.question.domain;

import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    //===생성 메서드===//

    //===수정 메서드===//
}
