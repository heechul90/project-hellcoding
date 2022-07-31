package com.heech.hellcoding.core.common.exception;

import com.heech.hellcoding.core.common.json.Error;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class JsonInvalidRequest extends CommonException {

    public static final String MESSAGE = "잘못된 요청입니다.";

    public JsonInvalidRequest() {
        super(MESSAGE);
    }

    public JsonInvalidRequest(List<Error> errors) {
        super(MESSAGE);
        addError(errors);
    }

    public JsonInvalidRequest(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }


}
