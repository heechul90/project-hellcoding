package com.heech.hellcoding.core.education.quiz.domain;

import com.heech.hellcoding.core.education.curriculum.domain.Curriculum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "education_quiz")
@DiscriminatorValue(value = "QUIZ")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends Curriculum {

    private String explanation;

}
