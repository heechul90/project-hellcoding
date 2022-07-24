package com.heech.hellcoding.core.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {

    private Long memberId;
    private String memberName;
    private String email;
    private String birthDate;
    private String author;
    private String gender;
    private String phoneNumber;
    private String address;

    @Builder
    public MemberDto(Long memberId, String memberName, String email, String birthDate, String author, String gender, String phoneNumber, String address) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.email = email;
        this.birthDate = birthDate;
        this.author = author;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
