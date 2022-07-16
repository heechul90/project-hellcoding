package com.heech.hellcoding.core.survey.questionnaire.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QuestionnaireRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Test
    public void saveTest() {
        //given

        //when

        //then
    }


}