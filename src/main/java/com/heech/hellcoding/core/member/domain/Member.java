package com.heech.hellcoding.core.member.domain;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.dto.UpdateMemberParam;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.springframework.util.StringUtils.*;

@Entity
/*@SequenceGenerator(
        name = "member_seq_generator",
        sequenceName = "member_seq",
        initialValue = 1, allocationSize = 100
)*/
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    /** 멤버고유ID */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    /** 이름 */
    @Column(name = "member_name", length = 30)
    private String name;

    /** 로그인ID */
    @Column(length = 80, unique = true, updatable = false)
    private String loginId;

    /** 비밀번호 */
    @Column(length = 80)
    private String password;

    /** 이메일 */
    @Column(length = 60)
    private String email;

    /** 생년월일 */
    @Column(length = 8)
    private String birthDate;

    /** 성별 */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "char")
    private GenderCode genderCode; //F, M
    /** 휴대폰번호 */
    @Embedded
    private Mobile mobile;

    /** 주소 */
    @Embedded
    private Address address;

    /** 가입일 */
    @Column(updatable = false)
    private LocalDateTime signupDate;

    /** 접속일 */
    private LocalDateTime signinDate;

    //===생성 메서드===//
    /**
     * 회원 생성
     */
    @Builder(builderClassName = "createMemberBuilder", builderMethodName = "createMemberBuilder")
    public Member(String name, String loginId, String password, String email, String birthDate, GenderCode genderCode, Mobile mobile, Address address) {
        Assert.hasText(name, "name은 필수값입니다.");
        Assert.hasText(loginId, "loginId는 필수값입니다.");
        Assert.hasText(password, "password는 필수값입니다.");
        Assert.hasText(email, "email은 필수값입니다.");

        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.genderCode = genderCode;
        this.mobile = mobile;
        this.address = address;
        this.signupDate = LocalDateTime.now();
        this.signinDate = LocalDateTime.now();
    }

    /**
     * 회원 수정
     */
    @Builder(builderClassName = "updateMemberBuilder", builderMethodName = "updateMemberBuilder")
    public void updateMember(UpdateMemberParam param) {
        if (hasText(param.getName())) this.name = param.getName();
        if (hasText(param.getEmail())) this.email = param.getEmail();
        if (hasText(param.getBirthDate())) this.birthDate = param.getBirthDate();
        if (param.getGenderCode() != null) this.genderCode = param.getGenderCode();
        if (param.getMobile() != null) this.mobile = param.getMobile();
        if (param.getAddress() != null) this.address = param.getAddress();
    }

    //=== 변경 메서드 ===//
    /**
     * 비밀번호 변경
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
