package com.heech.hellcoding.core.survey.question.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuestionRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    public void saveQuestionTest() throws Exception{
        //given

        //when

        //then
    }
}
