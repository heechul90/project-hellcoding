package com.heech.hellcoding.core.survey.question.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class QuestionServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    QuestionService questionService;

    @Test
    public void deleteQuestionTest() throws Exception{
        //given

        //when

        //then
    }
}
