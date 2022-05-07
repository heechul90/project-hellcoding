package com.heech.hellcoding.member.domain;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String birthday;
    private String genderCode;
    @Embedded
    private Mobile mobile;
    @Embedded
    private Address address;
    private LocalDateTime signupDate;
    private LocalDateTime signinDate;

    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
