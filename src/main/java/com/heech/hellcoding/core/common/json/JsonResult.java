package com.heech.hellcoding.core.common.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JsonResult<T> {

    private int count;
    private T data;
}
