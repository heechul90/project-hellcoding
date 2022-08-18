package com.heech.hellcoding.core.education.quiz.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "education_quiz_qeustion_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizQuestionOption {

    @Id @GeneratedValue
    @Column(name = "education_quiz_question_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_quiz_question_id")
    private QuizQuestion question;

    @Column(name = "option_content")
    private String content;

    private int sortNumber;

    private String imageUrl;


}
