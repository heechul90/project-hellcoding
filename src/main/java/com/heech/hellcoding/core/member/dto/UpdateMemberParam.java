package com.heech.hellcoding.core.member.dto;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Mobile;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UpdateMemberParam {

    private String name;
    private String email;

    private String birthDate;

    private GenderCode genderCode;

    private Mobile mobile;

    private Address address;
}
