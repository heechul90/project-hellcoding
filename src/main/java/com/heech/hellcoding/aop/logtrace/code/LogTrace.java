package com.heech.hellcoding.aop.logtrace.code;

import com.heech.hellcoding.aop.logtrace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);

}
