package com.heech.hellcoding.core.education.lesson.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LessonType {

    CONTENT("콘텐츠"), PLAN("지도안");

    private final String code;
}
