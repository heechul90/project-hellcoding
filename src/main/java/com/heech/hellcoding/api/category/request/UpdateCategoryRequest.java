package com.heech.hellcoding.api.category.request;

import com.heech.hellcoding.core.category.domain.ServiceName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class UpdateCategoryRequest {

    private Long upperCategoryId;
    private ServiceName serviceName;
    @PositiveOrZero
    private Integer categorySerialNumber;
    private String categoryName;
    private String categoryContent;
}
