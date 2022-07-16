package com.heech.hellcoding.api.category.request;

import com.heech.hellcoding.core.category.domain.ServiceName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class CreateCategoryRequest {

    private Long upperCategoryId;
    private ServiceName serviceName;
    @NotNull
    @PositiveOrZero
    private Integer categorySerialNumber;
    @NotEmpty
    private String categoryName;
    private String categoryContent;

}
