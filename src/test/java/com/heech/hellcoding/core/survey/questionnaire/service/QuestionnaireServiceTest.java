package com.heech.hellcoding.core.survey.questionnaire.service;

import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.survey.option.dto.UpdateOptionParam;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import com.heech.hellcoding.core.survey.question.dto.UpdateQuestionParam;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.UpdateQuestionnaireParam;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestionnaireServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    QuestionnaireService questionnaireService;

    private Questionnaire getQuestionnaire() {
        List<Option> options1 = new ArrayList<>();
        Option option1 = new Option(1, "test_content1");
        Option option2 = new Option(2, "test_content2");
        options1.add(option1);
        options1.add(option2);

        List<Option> options2 = new ArrayList<>();
        Option option3 = new Option(3, "test_content3");
        Option option4 = new Option(4, "test_content4");
        options2.add(option3);
        options2.add(option4);

        List<Question> questions = new ArrayList<>();
        Question question1 = Question.createQuestionBuilder()
                .title("test_title1")
                .questionOrder(1)
                .setting(Setting.OBJECTIVE)
                .options(options1)
                .build();

        Question question2 = Question.createQuestionBuilder()
                .title("test_title2")
                .questionOrder(2)
                .setting(Setting.OBJECTIVE)
                .options(options2)
                .build();
        questions.add(question1);
        questions.add(question2);

        Questionnaire questionnaire = new Questionnaire(
                "test_title",
                "test_description",
                "Y",
                LocalDateTime.now(),
                LocalDateTime.now(),
                questions
        );
        return questionnaire;
    }

    @Test
    void findQuestionnairesTest() {
        //given
        for (int i = 0; i < 50; i++) {
            com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire questionnaire = com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire.createQuestionnaireBuilder()
                    .title("test_title" + i)
                    .description("test_description" + i)
                    .isPeriod(i % 4 == 0 ? "N" : "Y")
                    .beginDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now())
                    .questions(null)
                    .build();
            em.persist(questionnaire);
        }

        //when
        QuestionnaireSearchCondition condition = new QuestionnaireSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire> content = questionnaireService.findQuestionnaires(condition, pageRequest);

        //then
        assertThat(content.getContent().size()).isEqualTo(10);
    }

    @Test
    void findQuestionnaireTest() {
        //given
        Questionnaire questionnaire = getQuestionnaire();
        Long savedId = questionnaireService.saveQuestionnaire(questionnaire);
        em.flush();
        em.clear();

        //when
        com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire findQuestionnaire = questionnaireService.findQuestionnaire(savedId);

        //then
        assertThat(findQuestionnaire.getTitle()).isEqualTo("test_title");
        assertThat(findQuestionnaire.getDescription()).isEqualTo("test_description");
        assertThat(findQuestionnaire.getQuestions().size()).isEqualTo(2);
    }

    @Test
    void saveQuestionnaireTest() {
        //given
        Questionnaire questionnaire = getQuestionnaire();

        //when
        Long savedId = questionnaireService.saveQuestionnaire(questionnaire);
        em.flush();
        em.clear();

        //then
        Questionnaire findQuestionnaire = questionnaireService.findQuestionnaire(savedId);
        assertThat(findQuestionnaire.getTitle()).isEqualTo("test_title");
        assertThat(findQuestionnaire.getDescription()).isEqualTo("test_description");
        assertThat(findQuestionnaire.getIsPeriod()).isEqualTo("Y");

        assertThat(findQuestionnaire.getQuestions().size()).isEqualTo(2);
        assertThat(findQuestionnaire.getQuestions()).extracting("title").containsExactly("test_title1", "test_title2");

        assertThat(findQuestionnaire.getQuestions().get(0).getOptions().size()).isEqualTo(2);
        assertThat(findQuestionnaire.getQuestions().get(0).getOptions()).extracting("content").containsExactly("test_content1", "test_content2");
        assertThat(findQuestionnaire.getQuestions().get(1).getOptions().size()).isEqualTo(2);
        assertThat(findQuestionnaire.getQuestions().get(1).getOptions()).extracting("content").containsExactly("test_content3", "test_content4");
    }

    @Test
    void updateQuestionnaireTest() {
        //given
        Questionnaire questionnaire = getQuestionnaire();
        Long savedId = questionnaireService.saveQuestionnaire(questionnaire);

        Questionnaire savedQuestionnaire = em.find(Questionnaire.class, savedId);

        List<UpdateOptionParam> options1 = new ArrayList<>();
        UpdateOptionParam option1 = new UpdateOptionParam(savedQuestionnaire.getQuestions().get(0).getOptions().get(0).getId(), 1, "update_content1");
        UpdateOptionParam option2 = new UpdateOptionParam(savedQuestionnaire.getQuestions().get(0).getOptions().get(1).getId(), 2, "update_content2");
        UpdateOptionParam option5 = new UpdateOptionParam(null, 5, "test_content5");
        options1.add(option1);
        options1.add(option2);
        options1.add(option5);

        List<UpdateOptionParam> options2 = new ArrayList<>();
        UpdateOptionParam option3 = new UpdateOptionParam(savedQuestionnaire.getQuestions().get(1).getOptions().get(0).getId(), 3, "update_content3");
        UpdateOptionParam option4 = new UpdateOptionParam(savedQuestionnaire.getQuestions().get(1).getOptions().get(1).getId(), 4, "update_content4");
        UpdateOptionParam option6 = new UpdateOptionParam(null, 6, "test_content6");
        options2.add(option3);
        options2.add(option4);
        options2.add(option6);

        List<UpdateOptionParam> options3 = new ArrayList<>();
        UpdateOptionParam option7 = new UpdateOptionParam(null, 7, "test_content7");
        UpdateOptionParam option8 = new UpdateOptionParam(null, 8, "test_content8");
        UpdateOptionParam option9 = new UpdateOptionParam(null, 9, "test_content9");

        options3.add(option7);
        options3.add(option8);
        options3.add(option9);

        List<UpdateQuestionParam> questions = new ArrayList<>();
        UpdateQuestionParam question1 = UpdateQuestionParam.updateQuestionBuilder()
                .questionId(savedQuestionnaire.getQuestions().get(0).getId())
                .questionTitle("update_title1")
                .questionOrder(1)
                .setting(Setting.OBJECTIVE)
                .options(options1)
                .build();

        UpdateQuestionParam question2 = UpdateQuestionParam.updateQuestionBuilder()
                .questionId(savedQuestionnaire.getQuestions().get(1).getId())
                .questionTitle("update_title2")
                .questionOrder(2)
                .setting(Setting.OBJECTIVE)
                .options(options2)
                .build();

        UpdateQuestionParam question3 = UpdateQuestionParam.updateQuestionBuilder()
                .questionId(null)
                .questionTitle("test_title3")
                .questionOrder(3)
                .setting(Setting.OBJECTIVE)
                .options(options3)
                .build();

        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        UpdateQuestionnaireParam updateParam = new UpdateQuestionnaireParam(
                "update_title",
                "update_description",
                "N",
                LocalDateTime.now(),
                LocalDateTime.now(),
                questions
        );
        em.flush();
        em.clear();

        //when
        questionnaireService.updateQuestionnaire(savedId, updateParam);
        em.flush();
        em.clear();

        //then
        com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire findQuestionnaire = questionnaireService.findQuestionnaire(savedId);
        assertThat(findQuestionnaire.getTitle()).isEqualTo("update_title");
        assertThat(findQuestionnaire.getDescription()).isEqualTo("update_description");
        assertThat(findQuestionnaire.getIsPeriod()).isEqualTo("N");
        assertThat(findQuestionnaire.getBeginDate()).isNull();
        assertThat(findQuestionnaire.getEndDate()).isNull();

        assertThat(findQuestionnaire.getQuestions().size()).isEqualTo(3);
        assertThat(findQuestionnaire.getQuestions()).extracting("title").containsExactly("update_title1", "update_title2", "test_title3");
        assertThat(findQuestionnaire.getQuestions()).extracting("questionOrder").containsExactly(1, 2, 3);

        assertThat(findQuestionnaire.getQuestions().get(0).getQuestionnaire()).isEqualTo(findQuestionnaire);
        assertThat(findQuestionnaire.getQuestions().get(1).getQuestionnaire()).isEqualTo(findQuestionnaire);
        assertThat(findQuestionnaire.getQuestions().get(2).getQuestionnaire()).isEqualTo(findQuestionnaire);

        assertThat(findQuestionnaire.getQuestions().get(0).getOptions().size()).isEqualTo(3);
        assertThat(findQuestionnaire.getQuestions().get(0).getOptions()).extracting("content")
                .containsExactly("update_content1", "update_content2", "test_content5");
        assertThat(findQuestionnaire.getQuestions().get(0).getOptions().get(0).getQuestion()).isEqualTo(findQuestionnaire.getQuestions().get(0));
        assertThat(findQuestionnaire.getQuestions().get(0).getOptions().get(1).getQuestion()).isEqualTo(findQuestionnaire.getQuestions().get(0));
        assertThat(findQuestionnaire.getQuestions().get(0).getOptions().get(2).getQuestion()).isEqualTo(findQuestionnaire.getQuestions().get(0));

        assertThat(findQuestionnaire.getQuestions().get(1).getOptions().size()).isEqualTo(3);
        assertThat(findQuestionnaire.getQuestions().get(1).getOptions()).extracting("content")
                .containsExactly("update_content3", "update_content4", "test_content6");
        assertThat(findQuestionnaire.getQuestions().get(1).getOptions().get(0).getQuestion()).isEqualTo(findQuestionnaire.getQuestions().get(1));
        assertThat(findQuestionnaire.getQuestions().get(1).getOptions().get(1).getQuestion()).isEqualTo(findQuestionnaire.getQuestions().get(1));
        assertThat(findQuestionnaire.getQuestions().get(1).getOptions().get(2).getQuestion()).isEqualTo(findQuestionnaire.getQuestions().get(1));
    }

    @Test
    void deleteQuestionnaireTest() {
        //given
        Questionnaire questionnaire = getQuestionnaire();
        Long savedId = questionnaireService.saveQuestionnaire(questionnaire);

        //when
        questionnaireService.deleteQuestionnaire(savedId);
        em.flush();
        em.clear();

        //then
        com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire findQuestionnaire = questionnaireService.findQuestionnaire(savedId);
        assertThat(findQuestionnaire.getIsDelete()).isEqualTo("N");
    }
}