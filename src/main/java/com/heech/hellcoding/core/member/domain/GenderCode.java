package com.heech.hellcoding.core.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderCode {
    M("남자"),
    F("여자");

    private final String codeName;
}
