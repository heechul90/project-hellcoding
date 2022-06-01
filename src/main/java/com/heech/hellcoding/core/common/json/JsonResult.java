package com.heech.hellcoding.core.common.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class JsonResult<T> {

    private LocalDateTime transaction_time;
    private HttpStatus status;
    private String message = "";

    private List<ObjectError> allErrors;
    private T data;

    public static <T> JsonResult<T> OK() {
        return (JsonResult<T>) JsonResult.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    public static <T> JsonResult<T> OK(T data) {
        return (JsonResult<T>) JsonResult.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.OK)
                .data(data)
                .build();
    }

    public static <T> JsonResult<T> ERROR(List<ObjectError> allErrors) {
        return (JsonResult<T>) JsonResult.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .allErrors(allErrors)
                .build();
    }
}
