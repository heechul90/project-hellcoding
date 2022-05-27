package com.heech.hellcoding.api.member.request;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class CreateMemberRequest {

    @NotEmpty
    private String memberName;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 8, max = 8)
    private String birthDate;
    @NotEmpty
    private String gender;
    @Length(min = 11, max = 11)
    private String phoneNumber;
    @Size(min = 5, max = 5)
    private String zipcode;
    @NotEmpty
    private String address;
    @NotEmpty
    private String detailAddress;

    public String getMobileNumberFirst() {
        return this.phoneNumber.substring(0, 2);
    }

    public String getMobileNumberMiddle() {
        return this.phoneNumber.substring(3, 6);
    }

    public String getMobileNumberLast() {
        return this.phoneNumber.substring(7, 10);
    }
}
