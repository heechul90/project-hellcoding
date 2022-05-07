package com.heech.hellcoding;

import com.heech.hellcoding.core.common.annotation.LoginMemberArgumentResolver;
import com.heech.hellcoding.core.interceptor.LoginChenckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginChenckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/front/member/add", "/user/sign/**", "/*.ico", "/lib/**", "/common/error",
                        "/front/*/css/**", "/front/*/js/**");
    }
}
