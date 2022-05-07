package com.heech.hellcoding.core.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    /** 멤버고유ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    /** 로그인ID */
    @Column(length = 80)
    private String loginId;
    /** 비밀번호 */
    @Column(length = 80)
    private String password;
    /** 이름 */
    @Column(length = 30)
    private String name;
    /** 이메일 */
    @Column(length = 60)
    private String email;
    /** 생년월일 */
    @Column(length = 8)
    private String birthday;
    /** 성별 */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "char")
    private GenderCode genderCode; //F, M
    /** 휴대폰번호 */
    @Embedded
    private Mobile mobile;
    /** 주소 */
    @Embedded
    private Address address;
    /** 가입일 */
    private LocalDateTime signupDate;
    /** 접속일 */
    private LocalDateTime signinDate;

    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    public Member(String loginId, String password, String name, String email, String birthday, GenderCode genderCode, Mobile mobile, Address address, LocalDateTime signupDate, LocalDateTime signinDate) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.genderCode = genderCode;
        this.mobile = mobile;
        this.address = address;
        this.signupDate = signupDate;
        this.signinDate = signinDate;
    }

    //=== 변경 메서드 ===//
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }


}