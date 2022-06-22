package com.heech.hellcoding.core.survey.questionnaire.domain;

import com.heech.hellcoding.core.survey.questionnaire.dto.UpdateQuestionnaireParam;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QuestionnaireTest {

    @PersistenceContext
    EntityManager em;

    private Questionnaire getQuestionnaire(String title, String description, String periodAt, LocalDateTime beginDate, LocalDateTime endDate) {
        Questionnaire questionnaire = Questionnaire.createQuestionnaireBuilder()
                .title(title)
                .description(description)
                .periodAt(periodAt)
                .beginDate(beginDate)
                .endDate(endDate)
                .build();
        return questionnaire;
    }

    @Test
    public void createQuestionnaireTest() throws Exception{
        //given
        Questionnaire questionnaire = getQuestionnaire("test_title", "test_description", "Y", LocalDateTime.now(), LocalDateTime.now());

        //when
        em.persist(questionnaire);
        em.flush();
        em.clear();

        //then
        Questionnaire findForm = em.find(Questionnaire.class, questionnaire.getId());
        assertThat(findForm.getTitle()).isEqualTo("test_title");
    }

    @Test
    public void updateQuestionnaireTest() throws Exception{
        //given
        Questionnaire questionnaire = getQuestionnaire("test_title", "test_description", "Y", LocalDateTime.now(), LocalDateTime.now());
        em.persist(questionnaire);
        em.flush();
        em.clear();

        //when
        Questionnaire findQuestionnaire = em.find(Questionnaire.class, questionnaire.getId());
        UpdateQuestionnaireParam param = new UpdateQuestionnaireParam();
        param.setTitle("update_title");
        param.setDescription("update_description");
        param.setPeriodAt("N");
        param.setBeginDate(LocalDateTime.now());
        param.setEndDate(LocalDateTime.now());
        findQuestionnaire.updateQuestionnaireBuilder().param(param).build();
        em.flush();
        em.clear();

        //then
        Questionnaire updatedQuestionnaire = em.find(Questionnaire.class, questionnaire.getId());
        assertThat(updatedQuestionnaire.getTitle()).isEqualTo("update_title");
        assertThat(updatedQuestionnaire.getBeginDate()).isNull();
    }

}