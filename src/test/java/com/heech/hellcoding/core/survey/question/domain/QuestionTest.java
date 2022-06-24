package com.heech.hellcoding.core.survey.question.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestionTest {

    @PersistenceContext
    EntityManager em;

    private Question getQuestion() {
        Question question = Question.createQuestionBuilder()
                .title("test_question_title")
                .questionOrder(1)
                .setting(Setting.OBJECTIVE)
                .options(null)
                .build();
        return question;
    }

    @Test
    public void createQuestionTest() throws Exception{
        //given
        Question question = getQuestion();

        //when
        em.persist(question);
        em.flush();
        em.clear();

        //then
        Question findQuestion = em.find(Question.class, question.getId());
        assertThat(findQuestion.getTitle()).isEqualTo("test_question_title");
        assertThat(findQuestion.getQuestionOrder()).isEqualTo(1);
        assertThat(findQuestion.getSetting()).isEqualTo(Setting.OBJECTIVE);
        assertThat(findQuestion.getOptions().size()).isZero();
        assertThatThrownBy(() -> findQuestion.getOptions().get(0))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

}