package com.heech.hellcoding.core.common.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
public class JsonResult<T> {

    private int count;
    private T data;
}
