package com.heech.hellcoding.api.member.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UpdateMemberRequest {

    private String memberName;
    @Email
    private String email;
    private String password;
}
