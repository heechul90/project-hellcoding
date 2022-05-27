package com.heech.hellcoding.core.common.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class JsonResult<T> {

    private LocalDateTime transaction_time;
    private String status;
    private String description;

    private T data;

    public static <T> JsonResult<T> OK() {
        return (JsonResult<T>) JsonResult.builder()
                .transaction_time(LocalDateTime.now())
                .status("OK")
                .description("From MemberAPI")
                .build();
    }

    public static <T> JsonResult<T> OK(T data) {
        return (JsonResult<T>) JsonResult.builder()
                .transaction_time(LocalDateTime.now())
                .status("data-OK")
                .description("From MemberAPI")
                .data(data)
                .build();
    }

    public static <T> JsonResult<T> ERROR() {
        return (JsonResult<T>) JsonResult.builder()
                .transaction_time(LocalDateTime.now())
                .status("ERROR")
                .description("From MemberAPI")
                .build();
    }
}
