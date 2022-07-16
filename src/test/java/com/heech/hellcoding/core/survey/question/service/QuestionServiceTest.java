package com.heech.hellcoding.core.survey.question.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.survey.SurveyTestConfig;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(SurveyTestConfig.class)
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
