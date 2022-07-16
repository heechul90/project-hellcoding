package com.heech.hellcoding.core.category.dto;

import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.common.dto.CommonSearchCondition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySearchCondition extends CommonSearchCondition {

    private ServiceName searchServiceName;
}
