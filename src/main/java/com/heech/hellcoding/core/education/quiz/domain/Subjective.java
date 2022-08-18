package com.heech.hellcoding.core.education.quiz.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "SBJECTIVE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subjective extends QuizQuestion {

    private SubjectiveType subjectiveType;
}
