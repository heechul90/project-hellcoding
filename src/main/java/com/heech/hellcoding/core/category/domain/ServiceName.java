package com.heech.hellcoding.core.category.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceName {

    SHOP("히치쇼핑"),
    LESSON("히치런");

    private final String name;
}
