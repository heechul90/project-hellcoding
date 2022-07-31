package com.heech.hellcoding.api.member;

import com.heech.hellcoding.core.common.exception.CommonException;
import com.heech.hellcoding.core.common.json.Error;
import com.heech.hellcoding.core.common.json.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiMemberControllerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<JsonResult> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<Error> errors = e.getFieldErrors().stream()
                .map(error -> new Error(
                        error.getField(),
                        error.getDefaultMessage())
                ).collect(Collectors.toList());

        ResponseEntity<JsonResult> response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(JsonResult.ERROR(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", errors));

        return response;
    }

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public ResponseEntity<JsonResult> ApiMemberException(CommonException e) {
        ResponseEntity<JsonResult> response = ResponseEntity.status(e.status())
                .body(JsonResult.ERROR(e.status(), e.getMessage(), e.getErrors()));
        return response;
    }
}
