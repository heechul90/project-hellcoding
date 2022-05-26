package com.heech.hellcoding.core.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberDto {

    private String memberName;
    private String email;
    private String birthDate;
    private String gender;
    private String phoneNumber;
    private String address;

}
