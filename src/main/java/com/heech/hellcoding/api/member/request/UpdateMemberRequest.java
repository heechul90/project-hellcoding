package com.heech.hellcoding.api.member.request;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.dto.UpdateMemberParam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Getter
@Setter
public class UpdateMemberRequest {

    private String memberName;

    @Email
    private String email;

    @Length(min = 8, max = 8)
    private String birthday;

    private GenderCode genderCode;

    private String mobileNumber;

    @Length(min = 5, max = 5)
    private String zipcode;
    private String address;
    private String detailAddress;

    public UpdateMemberParam toUpdateMemberParam() {
        return UpdateMemberParam.builder()
                .name(this.memberName)
                .email(this.email)
                .birthDate(this.birthday)
                .genderCode(this.genderCode)
                .mobile(new Mobile(this.mobileNumber.split("-")[0], this.mobileNumber.split("-")[1], this.mobileNumber.split("-")[2]))
                .address(new Address(this.zipcode, this.address, this.detailAddress))
                .build();
    }


}
