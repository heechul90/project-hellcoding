package com.heech.hellcoding.core.survey.questionnaire.domain;

import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QuestionnaireTest {

    @PersistenceContext
    EntityManager em;

    private Questionnaire getQuestionnaire(String title, String description, String isPeriod, LocalDateTime beginDate, LocalDateTime endDate, List<Question> questions) {
        Questionnaire questionnaire = Questionnaire.createQuestionnaireBuilder()
                .title(title)
                .description(description)
                .isPeriod(isPeriod)
                .beginDate(beginDate)
                .endDate(endDate)
                .questions(questions)
                .build();
        return questionnaire;
    }

    @Test
    public void createQuestionnaireTest() throws Exception{
        //given
        List<Question> questions = new ArrayList<>();

        List<Option> options = new ArrayList<>();
        Option option1 = new Option(1, "test_content1");
        Option option2 = new Option(2, "test_content2");
        options.add(option1);
        options.add(option2);

        Question question1 = new Question("test_title", 1, Setting.OBJECTIVE, options);
        questions.add(question1);

        Questionnaire questionnaire = getQuestionnaire("test_title", "test_description", "Y", LocalDateTime.now(), LocalDateTime.now(), questions);

        //when
        em.persist(questionnaire);

        //then
        assertThat(questionnaire.getQuestions().get(0).getTitle()).isEqualTo("test_title");
        assertThat(question1.getQuestionnaire().getTitle()).isEqualTo("test_title");
        assertThat(question1.getQuestionnaire().getDescription()).isEqualTo("test_description");
        assertThat(question1.getOptions().size()).isEqualTo(2);
        assertThat(question1.getOptions()).extracting("content").containsExactly("test_content1", "test_content2");
        assertThat(option1.getQuestion().getTitle()).isEqualTo(option2.getQuestion().getTitle());

        em.flush();
        em.clear();
        Questionnaire findQuestionnaire = em.find(Questionnaire.class, questionnaire.getId());
        assertThat(findQuestionnaire.getTitle()).isEqualTo("test_title");
        assertThat(findQuestionnaire.getQuestions().get(0).getTitle()).isEqualTo("test_title");
        assertThat(findQuestionnaire.getQuestions().get(0).getOptions()).extracting("content").containsExactly("test_content1", "test_content2");
    }

    @Test
    public void updateQuestionnaireTest() throws Exception{
        //given
        List<Question> questions = new ArrayList<>();

        List<Option> options = new ArrayList<>();
        Option option1 = new Option(1, "test_content1");
        Option option2 = new Option(2, "test_content2");
        options.add(option1);
        options.add(option2);

        Question question1 = new Question("test_title", 1, Setting.OBJECTIVE, options);
        questions.add(question1);

        Questionnaire questionnaire = getQuestionnaire("test_title", "test_description", "Y", LocalDateTime.now(), LocalDateTime.now(), questions);
        em.persist(questionnaire);
        em.flush();
        em.clear();

        //when
        Questionnaire findQuestionnire = em.find(Questionnaire.class, questionnaire.getId());
        findQuestionnire.updateQuestionnaireBuilder()
                .title("update_title")
                .description("update_description")
                .isPeriod("N")
                .beginDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .build();
        em.flush();
        em.clear();

        //then
        Questionnaire updatedQuestionnaire = em.find(Questionnaire.class, questionnaire.getId());
        assertThat(updatedQuestionnaire.getTitle()).isEqualTo("update_title");
        assertThat(updatedQuestionnaire.getDescription()).isEqualTo("update_description");
        assertThat(updatedQuestionnaire.getIsPeriod()).isEqualTo("N");
        assertThat(updatedQuestionnaire.getBeginDate()).isNull();
        assertThat(updatedQuestionnaire.getEndDate()).isNull();
    }

}