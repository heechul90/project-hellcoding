package com.heech.hellcoding.front.user.join.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class JoinForm {

    /** 로그인ID */
    @NotEmpty
    private String loginId;
    /** 비밀번호 */
    @NotEmpty
    private String password;
    /** 이름 */
    @NotEmpty
    private String name;
    /** 이메일 */
    @NotEmpty
    @Email
    private String email;
    /** 생년월일 년 */
    @NotEmpty
    private String birthYear;
    /** 생년월일 월 */
    @NotEmpty
    private String birthMonth;
    /** 생년월일 일 */
    @NotEmpty
    private String birthDay;
    /** 성별 */
    @NotEmpty
    private String genderCode; //F, M
    /** 휴대폰번호 첫자리 */
    @NotEmpty
    private String mobileNumberFirst;
    /** 휴대폰번호 중간자리 */
    @NotEmpty
    private String mobileNumberMiddle;
    /** 휴대폰번호 마지막자리 */
    @NotEmpty
    private String mobileNumberLast;
    /** 우편번호 */
    private String zipcode;
    /** 주소 */
    private String address;
    /** 상세주소 */
    private String detailAddress;
}
