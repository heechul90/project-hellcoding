package com.heech.hellcoding.api.member.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class UpdateMemberRequest {

    private String memberName;
    private String password;
}
