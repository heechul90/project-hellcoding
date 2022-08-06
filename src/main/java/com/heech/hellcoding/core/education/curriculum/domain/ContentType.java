package com.heech.hellcoding.core.education.curriculum.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {

    VIDEO("영상"), LIVE("라이브"), QUIZ("퀴즈");

    private final String code;

}
