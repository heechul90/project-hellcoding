package com.heech.hellcoding.core.education.content.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "education_content_quiz")
@DiscriminatorValue(value = "QUIZ")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends Content {

    private String explanation;

}
