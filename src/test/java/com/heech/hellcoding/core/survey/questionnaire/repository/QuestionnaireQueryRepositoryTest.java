package com.heech.hellcoding.core.survey.questionnaire.repository;

import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QuestionnaireQueryRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired QuestionnaireQueryRepository questionnaireQueryRepository;

    @Test
    void findQuestionnairesTest() {
        //given
        for (int i = 0; i < 50; i++) {
            Questionnaire questionnaire = Questionnaire.createQuestionnaireBuilder()
                    .title("test_title" + i)
                    .description("test_description" + i)
                    .isPeriod("Y")
                    .beginDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now())
                    .build();
            em.persist(questionnaire);
        }
        em.flush();
        em.clear();

        //when
        QuestionnaireSearchCondition condition = new QuestionnaireSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Questionnaire> content = questionnaireQueryRepository.findQuestionnaires(condition, pageRequest);

        //then
        assertThat(content.getContent().size()).isEqualTo(10);
    }
}