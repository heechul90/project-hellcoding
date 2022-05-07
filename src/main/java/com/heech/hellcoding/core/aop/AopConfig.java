package com.heech.hellcoding.core.aop;

import com.heech.hellcoding.core.aop.aspect.LogTraceAspect;
import com.heech.hellcoding.core.aop.code.LogTrace;
import com.heech.hellcoding.core.aop.code.ThreadLocalLogTrace;
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