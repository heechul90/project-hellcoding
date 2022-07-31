package com.heech.hellcoding.api.member.request;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.exception.JsonInvalidRequest;
import com.heech.hellcoding.core.common.json.Error;
import com.heech.hellcoding.core.member.domain.AuthorCode;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateMemberRequest {

    @NotBlank
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
    @NotNull
    private AuthorCode authorCode;
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

    public Member toEntity() {
        return Member.createMemberBuilder()
                .name(this.memberName)
                .loginId(this.loginId)
                .password(this.password)
                .email(this.email)
                .birthDate(this.birthDate)
                .authorCode(this.authorCode)
                .genderCode(this.gender.equals("M") ? GenderCode.M : GenderCode.F)
                .mobile(new Mobile(this.phoneNumber.substring(0, 3), this.phoneNumber.substring(3, 7), this.phoneNumber.substring(7, 11)))
                .address(new Address(this.zipcode, this.address, this.detailAddress))
                .build();
    }

    //validate check
    public void validate() {
        List<Error> errors = new ArrayList<>();
        if (this.memberName.startsWith("이")) errors.add(new Error(memberName.toString(), "이씨는 회원가입을 할 수 없습니다."));
        if (this.loginId.startsWith("a")) errors.add(new Error(loginId.toString(), "로그인 아이디는 a로 시작할 수 없습니다."));

        if (errors.size() > 0) {
            throw new JsonInvalidRequest(errors);
        }
    }
}
