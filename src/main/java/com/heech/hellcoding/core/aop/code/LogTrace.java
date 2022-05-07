package com.heech.hellcoding.core.aop.code;

import com.heech.hellcoding.core.aop.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);

}
