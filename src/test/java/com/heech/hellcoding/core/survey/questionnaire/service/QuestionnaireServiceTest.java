package com.heech.hellcoding.core.survey.questionnaire.service;

import com.heech.hellcoding.core.survey.option.dto.OptionDto;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import com.heech.hellcoding.core.survey.question.dto.QuestionDto;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.CreateUpdateQuestionnaireDto;
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

    private CreateUpdateQuestionnaireDto getQuestionnaireDto() {
        List<OptionDto> options1 = new ArrayList<>();
        OptionDto option1 = new OptionDto(1, "test_content1");
        OptionDto option2 = new OptionDto(2, "test_content2");
        options1.add(option1);
        options1.add(option2);

        List<OptionDto> options2 = new ArrayList<>();
        OptionDto option3 = new OptionDto(3, "test_content3");
        OptionDto option4 = new OptionDto(4, "test_content4");
        options2.add(option3);
        options2.add(option4);

        List<QuestionDto> questions = new ArrayList<>();
        QuestionDto question1 = QuestionDto.createQuestionBuiler()
                .questionTitle("test_title1")
                .questionOrder(1)
                .setting(Setting.OBJECTIVE)
                .options(options1)
                .build();

        QuestionDto question2 = QuestionDto.createQuestionBuiler()
                .questionTitle("test_title2")
                .questionOrder(2)
                .setting(Setting.OBJECTIVE)
                .options(options2)
                .build();
        questions.add(question1);
        questions.add(question2);

        CreateUpdateQuestionnaireDto saveParam = new CreateUpdateQuestionnaireDto(
                "test_title",
                "test_description",
                "Y",
                LocalDateTime.now(),
                LocalDateTime.now(),
                questions
        );
        return saveParam;
    }

    @Test
    void findQuestionnairesTest() {
        //given
        for (int i = 0; i < 50; i++) {
            Questionnaire questionnaire = Questionnaire.createQuestionnaireBuilder()
                    .title("test_title" + i)
                    .description("test_description" + i)
                    .periodAt(i % 4 == 0 ? "N" : "Y")
                    .beginDate(LocalDateTime.now())
                    .endDate(LocalDateTime.now())
                    .questions(null)
                    .build();
            em.persist(questionnaire);
        }

        //when
        QuestionnaireSearchCondition condition = new QuestionnaireSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Questionnaire> content = questionnaireService.findQuestionnaires(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(50);
        assertThat(content.getContent().size()).isEqualTo(10);
    }

    @Test
    void findQuestionnaireTest() {
        //given
        CreateUpdateQuestionnaireDto saveParam = getQuestionnaireDto();
        Long savedId = questionnaireService.saveQuestionnaire(saveParam);
        em.flush();
        em.clear();

        //when
        Questionnaire findQuestionnaire = questionnaireService.findQuestionnaire(savedId);

        //then
        assertThat(findQuestionnaire.getTitle()).isEqualTo("test_title");
        assertThat(findQuestionnaire.getDescription()).isEqualTo("test_description");
        assertThat(findQuestionnaire.getQuestions().size()).isEqualTo(2);
    }

    @Test
    void saveQuestionnaireTest() {
        //given
        CreateUpdateQuestionnaireDto saveParam = getQuestionnaireDto();

        //when
        Long savedId = questionnaireService.saveQuestionnaire(saveParam);
        em.flush();
        em.clear();

        //then
        Questionnaire findQuestionnaire = questionnaireService.findQuestionnaire(savedId);
        assertThat(findQuestionnaire.getTitle()).isEqualTo("test_title");
        assertThat(findQuestionnaire.getDescription()).isEqualTo("test_description");
        assertThat(findQuestionnaire.getPeriodAt()).isEqualTo("Y");

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
        CreateUpdateQuestionnaireDto saveParam = getQuestionnaireDto();
        Long savedId = questionnaireService.saveQuestionnaire(saveParam);

        Questionnaire savedQuestionnaire = em.find(Questionnaire.class, savedId);

        List<OptionDto> options1 = new ArrayList<>();
        OptionDto option1 = new OptionDto(savedQuestionnaire.getQuestions().get(0).getOptions().get(0).getId(), 1, "update_content1");
        OptionDto option2 = new OptionDto(savedQuestionnaire.getQuestions().get(0).getOptions().get(1).getId(), 2, "update_content2");
        OptionDto option5 = new OptionDto(5, "test_content5");
        options1.add(option1);
        options1.add(option2);
        options1.add(option5);

        List<OptionDto> options2 = new ArrayList<>();
        OptionDto option3 = new OptionDto(savedQuestionnaire.getQuestions().get(1).getOptions().get(0).getId(), 3, "update_content3");
        OptionDto option4 = new OptionDto(savedQuestionnaire.getQuestions().get(1).getOptions().get(1).getId(), 4, "update_content4");
        OptionDto option6 = new OptionDto(6, "test_content6");
        options2.add(option3);
        options2.add(option4);
        options2.add(option6);

        List<OptionDto> options3 = new ArrayList<>();
        OptionDto option7 = new OptionDto(7, "test_content7");
        OptionDto option8 = new OptionDto(8, "test_content8");
        OptionDto option9 = new OptionDto(9, "test_content9");

        options3.add(option7);
        options3.add(option8);
        options3.add(option9);

        List<QuestionDto> questions = new ArrayList<>();
        QuestionDto question1 = QuestionDto.updateQuestionBuilder()
                .questionId(savedQuestionnaire.getQuestions().get(0).getId())
                .questionTitle("update_title1")
                .questionOrder(1)
                .setting(Setting.OBJECTIVE)
                .options(options1)
                .build();

        QuestionDto question2 = QuestionDto.updateQuestionBuilder()
                .questionId(savedQuestionnaire.getQuestions().get(1).getId())
                .questionTitle("update_title2")
                .questionOrder(2)
                .setting(Setting.OBJECTIVE)
                .options(options2)
                .build();

        QuestionDto question3 = QuestionDto.createQuestionBuiler()
                .questionTitle("test_title3")
                .questionOrder(3)
                .setting(Setting.OBJECTIVE)
                .options(options3)
                .build();


        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        CreateUpdateQuestionnaireDto updateParam = new CreateUpdateQuestionnaireDto(
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
        Questionnaire findQuestionnaire = questionnaireService.findQuestionnaire(savedId);
        assertThat(findQuestionnaire.getTitle()).isEqualTo("update_title");
        assertThat(findQuestionnaire.getDescription()).isEqualTo("update_description");
        assertThat(findQuestionnaire.getPeriodAt()).isEqualTo("N");
        assertThat(findQuestionnaire.getBeginDate()).isNull();
        assertThat(findQuestionnaire.getEndDate()).isNull();

        for (Question question : findQuestionnaire.getQuestions()) {
            System.out.println("question.getTitle() = " + question.getTitle());
        }

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
        CreateUpdateQuestionnaireDto saveParam = getQuestionnaireDto();
        Long savedId = questionnaireService.saveQuestionnaire(saveParam);

        //when
        questionnaireService.deleteQuestionnaire(savedId);
        em.flush();
        em.clear();

        //then
        Questionnaire findQuestionnaire = questionnaireService.findQuestionnaire(savedId);
        assertThat(findQuestionnaire.getUseAt()).isEqualTo("N");
    }
}