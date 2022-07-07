package com.heech.hellcoding.core.education.lesson.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AgeCode {

    ALL("전체"), PARENT("부모"), ADULT("성인"), DETAIL("상세입력");

    private final String code;
}
