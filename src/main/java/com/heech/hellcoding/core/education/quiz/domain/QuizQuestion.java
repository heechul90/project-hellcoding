package com.heech.hellcoding.core.education.quiz.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "education_quiz_question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizQuestion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_quiz_question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_quiz_id")
    private Quiz quiz;

    @Column(name = "question_content")
    private String content;
}
