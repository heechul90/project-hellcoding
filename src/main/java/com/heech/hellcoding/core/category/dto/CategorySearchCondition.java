package com.heech.hellcoding.core.category.dto;

import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.common.dto.CommonSearchCondition;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategorySearchCondition extends CommonSearchCondition {

    @NotNull(message = "서비스를 선택해주세요.")
    private ServiceName searchServiceName;
}
