package com.heech.hellcoding.api.member.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class UpdateMemberRequest {

    private String memberName;
    @Email
    private String email;
}
