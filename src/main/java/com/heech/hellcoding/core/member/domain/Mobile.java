package com.heech.hellcoding.core.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mobile {

    /** 휴대폰번호(첫번째) */
    @Column(length = 3)
    private String mobileNumberFirst;
    /** 휴대폰번호(가운데) */
    @Column(length = 4)
    private String mobileNumberMiddle;
    /** 휴대폰번호(마지막) */
    @Column(length = 4)
    private String mobileNumberLast;

    public Mobile(String mobileNumberFirst, String mobileNumberMiddle, String mobileNumberLast) {
        this.mobileNumberFirst = mobileNumberFirst;
        this.mobileNumberMiddle = mobileNumberMiddle;
        this.mobileNumberLast = mobileNumberLast;
    }
}
