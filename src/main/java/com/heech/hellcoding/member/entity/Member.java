package com.heech.hellcoding.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
