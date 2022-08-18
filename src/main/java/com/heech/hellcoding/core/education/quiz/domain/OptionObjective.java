package com.heech.hellcoding.core.education.quiz.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionObjective extends QuizQuestionOption {

    private boolean isExplanation;
    private String explanation;
    private boolean isCorrect;

}
