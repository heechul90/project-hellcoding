package com.heech.hellcoding.core.survey;

import com.heech.hellcoding.core.survey.option.repository.OptionRepository;
import com.heech.hellcoding.core.survey.option.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class SurveyTestConfig {

    @Autowired
    OptionRepository optionRepository;

    @Bean
    public OptionService optionService() {
        return new OptionService(optionRepository);
    }
}
