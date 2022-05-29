package com.heech.hellcoding.core.member.dto;

import com.heech.hellcoding.core.common.dto.CommonSearchCondition;
import com.heech.hellcoding.core.member.domain.GenderCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchCondition extends CommonSearchCondition {

    private GenderCode searchGender;

}
