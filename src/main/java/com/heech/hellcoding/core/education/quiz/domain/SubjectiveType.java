package com.heech.hellcoding.core.education.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SubjectiveType {

    SHORT("단답형"),
    DISCRIPTION("서술형");

    private final String typeName;

}
