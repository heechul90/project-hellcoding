package com.heech.hellcoding.aop.logtrace;

import com.heech.hellcoding.aop.logtrace.aspect.LogTraceAspect;
import com.heech.hellcoding.aop.logtrace.code.LogTrace;
import com.heech.hellcoding.aop.logtrace.code.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}