package com.heech.hellcoding.core.survey.result.repository;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.survey.option.domain.Option;
import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.result.domain.QuestionnaireResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestionnaireResultRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    QuestionnaireResultRepository questionnaireResultRepository;

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
    void countByMemberIdByQuestionnaireId() {
        //given
        List<Question> questions = new ArrayList<>();

        List<Option> options = new ArrayList<>();
        Option option1 = new Option(1, "test_content1");
        Option option2 = new Option(2, "test_content2");
        options.add(option1);
        options.add(option2);

        Question question1 = new Question("test_title", 1, Setting.OBJECTIVE, options);
        questions.add(question1);

        Questionnaire questionnaire = getQuestionnaire("test_title111", "test_description", "Y", LocalDateTime.now(), LocalDateTime.now(), questions);
        em.persist(questionnaire);

        Member member = Member.builder()
                .name("test_member")
                .loginId("test_member")
                .password("test_password")
                .email("test_email@google.com")
                .build();
        em.persist(member);

        QuestionnaireResult questionnaireResult = QuestionnaireResult.createQuestionnaireResultBuilder()
                .member(member)
                .option(option1)
                .build();
        em.persist(questionnaireResult);
        em.flush();
        em.clear();

        //when
        int count = questionnaireResultRepository.countQuestionnaireResult(member.getId(), questionnaire.getId());

        //then
        assertThat(count).isGreaterThan(0);
    }
}