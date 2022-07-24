package com.heech.hellcoding.api.member.request;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
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
        return this.phoneNumber.substring(0, 3);
    }

    public String getMobileNumberMiddle() {
        return this.phoneNumber.substring(3, 7);
    }

    public String getMobileNumberLast() {
        return this.phoneNumber.substring(7, 11);
    }

    public Member toEntity() {
        return Member.createMemberBuilder()
                .name(this.memberName)
                .loginId(this.loginId)
                .password(this.password)
                .email(this.email)
                .genderCode(this.gender == "M" ? GenderCode.M : GenderCode.F)
                .mobile(new Mobile(getMobileNumberFirst(), getMobileNumberMiddle(), getMobileNumberLast()))
                .address(new Address(this.zipcode, this.address, this.detailAddress))
                .build();
    }
}
