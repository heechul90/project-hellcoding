package com.heech.hellcoding.core.education.lesson.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Level {

    LEVEL001("초급"), LEVEL002("중급"), LEVEL003("고급");

    private final String code;
}
