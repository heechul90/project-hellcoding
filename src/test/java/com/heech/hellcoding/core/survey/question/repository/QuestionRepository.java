package com.heech.hellcoding.core.survey.question.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class QuestionRepository {

    @PersistenceContext
    EntityManager em;

    @Autowired
    QuestionRepository questionRepository;

}
