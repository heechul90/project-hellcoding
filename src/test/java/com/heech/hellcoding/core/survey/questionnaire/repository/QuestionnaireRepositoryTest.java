package com.heech.hellcoding.core.survey.questionnaire.repository;

import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireSearchCondition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestionnaireRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @Test
    @Rollback(value = false)
    public void findQuestionnairesTest() throws Exception{
        //given
        for (int i = 0; i < 50; i++) {
            Questionnaire questionnaire = Questionnaire.createQuestionnaireBuilder()
                    .title("test_title" + i)
                    .description("test_description" + i)
                    .periodAt("Y")
                    .beginDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now())
                    .build();
            questionnaireRepository.save(questionnaire);
        }
        em.flush();
        em.clear();

        //when
        QuestionnaireSearchCondition condition = new QuestionnaireSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Questionnaire> content = questionnaireRepository.findQuestionnaires(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(10);
    }

}