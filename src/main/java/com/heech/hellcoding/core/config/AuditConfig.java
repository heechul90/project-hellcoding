package com.heech.hellcoding.core.config;

import com.heech.hellcoding.core.common.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@EnableJpaAuditing
@Configuration
public class AuditConfig implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        //TODO spring security 적용후 로그인한 유저 가져와야함.
        String createdBy = UUID.randomUUID().toString();
        return Optional.of(createdBy);
    }
}
