package com.heech.hellcoding.core.survey;

import com.heech.hellcoding.core.survey.option.repository.OptionRepository;
import com.heech.hellcoding.core.survey.option.service.OptionService;
import com.heech.hellcoding.core.survey.question.repository.QuestionRepository;
import com.heech.hellcoding.core.survey.question.service.QuestionService;
import com.heech.hellcoding.core.survey.questionnaire.repository.QuestionnaireQueryRepository;
import com.heech.hellcoding.core.survey.questionnaire.repository.QuestionnaireRepository;
import com.heech.hellcoding.core.survey.questionnaire.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class SurveyTestConfig {

    @PersistenceContext EntityManager em;

    @Autowired OptionRepository optionRepository;

    @Autowired QuestionRepository questionRepository;

    @Autowired QuestionnaireRepository questionnaireRepository;

    @Bean
    public OptionService optionService() {
        return new OptionService(optionRepository);
    }

    @Bean
    public QuestionService questionService() {
        return new QuestionService(questionRepository);
    }

    @Bean
    public QuestionnaireQueryRepository questionnaireQueryRepository() {
        return new QuestionnaireQueryRepository(em);
    }

    @Bean
    public QuestionnaireService questionnaireService() {
        return new QuestionnaireService(questionnaireRepository, questionnaireQueryRepository());
    }
}
