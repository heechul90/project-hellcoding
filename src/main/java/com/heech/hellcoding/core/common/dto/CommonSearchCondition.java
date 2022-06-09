package com.heech.hellcoding.core.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonSearchCondition {

    /** 검색 조건 */
    private SearchCondition searchCondition;
    private String searchKeyword;

}
