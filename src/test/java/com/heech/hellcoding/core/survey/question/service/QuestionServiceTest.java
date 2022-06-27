package com.heech.hellcoding.core.survey.question.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestionServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    QuestionService questionService;

    @Test
    void deleteQuestionTest() {
        //given
        Question question = Question.createQuestionBuilder()
                .title("test_question_title")
                .questionOrder(1)
                .setting(Setting.OBJECTIVE)
                .build();
        em.persist(question);
        em.flush();
        em.clear();

        //when
        questionService.deleteQuestion(question.getId());

        //then
        assertThatThrownBy(() -> questionService.findQuestion(question.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("잘못된");
    }
}
