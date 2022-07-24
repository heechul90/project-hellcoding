package com.heech.hellcoding.api.member.request;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.AuthorCode;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.dto.UpdateMemberParam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Email;

import static org.springframework.util.StringUtils.*;

@Getter
@Setter
public class UpdateMemberRequest {

    private String memberName;

    @Email
    private String email;

    @Length(min = 8, max = 8)
    private String birthDate;

    private AuthorCode authorCode;
    private String gender;

    @Length(min = 11, max = 11)
    private String phoneNumber;

    @Length(min = 5, max = 5)
    private String zipcode;
    private String address;
    private String detailAddress;

    public UpdateMemberParam toUpdateMemberParam() {
        return UpdateMemberParam.builder()
                .name(hasText(this.memberName) ? this.memberName : null)
                .email(hasText(this.email) ? this.email : null)
                .birthDate(hasText(this.birthDate) ? this.birthDate : null)
                .authorCode(this.authorCode != null ? this.authorCode : null)
                .genderCode(hasText(this.gender) ? this.gender.equals("M") ? GenderCode.M : GenderCode.F : null)
                .mobile(hasText(this.phoneNumber) ? new Mobile(this.phoneNumber.substring(0, 3), this.phoneNumber.substring(3, 7), this.phoneNumber.substring(7, 11)) : null)
                .address(hasText(this.zipcode) && hasText(this.address) && hasText(this.detailAddress) ? new Address(this.zipcode, this.address, this.detailAddress) : null)
                .build();
    }
}
