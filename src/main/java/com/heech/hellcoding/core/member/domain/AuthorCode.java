package com.heech.hellcoding.core.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthorCode {

    ROLE_USER("일반유저"),
    ROLE_ADMIN("관리자");

    private final String codeName;

}
