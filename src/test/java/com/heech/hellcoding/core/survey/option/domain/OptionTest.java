package com.heech.hellcoding.core.survey.option.domain;

import com.heech.hellcoding.core.survey.question.domain.Question;
import com.heech.hellcoding.core.survey.question.domain.Setting;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OptionTest {

    @PersistenceContext
    EntityManager em;

    private Question getQuestion(String title, int questionOrder, Setting setting, List<Option> options) {
        return Question.createQuestionBuilder()
                .title(title)
                .questionOrder(questionOrder)
                .setting(setting)
                .options(options)
                .build();
    }

    private Option getOption() {
        return Option.createOptionBuilder()
                .optionOrder(1)
                .content("test_content")
                .build();
    }

    @Test
    public void createOptionTest() {
        //given
        Option option = getOption();

        //when
        em.persist(option);
        em.flush();
        em.clear();

        //then
        Option findOption = em.find(Option.class, option.getId());
        assertThat(findOption.getOptionOrder()).isEqualTo(1);
        assertThat(findOption.getContent()).isEqualTo("test_content");
    }

    @Test
    public void updateOptionTest() {
        //given
        List<Option> options = new ArrayList<>();
        Option option = getOption();
        options.add(option);
        Question question = getQuestion("test_title", 1, Setting.OBJECTIVE, options);
        em.persist(question);

        //when
        option.updateOptionBuilder()
                .question(question)
                .optionOrder(5)
                .content("update_content")
                .build();
        em.flush();
        em.clear();

        //then
        Question findQuestion = em.find(Question.class, question.getId());
        assertThat(findQuestion.getOptions().size()).isEqualTo(1);
        assertThat(findQuestion.getOptions()).extracting("content")
                .containsExactly("update_content");
        assertThat(findQuestion.getOptions()).extracting("optionOrder")
                .containsExactly(5);
    }

    @Test
    public void addOptionTest() {
        //given
        List<Option> options = new ArrayList<>();
        Option option = getOption();
        options.add(option);
        Question question = getQuestion("test_title", 1, Setting.OBJECTIVE, options);
        em.persist(question);

        //when
        Option addOption = Option.addOptionBuilder()
                .question(question)
                .optionOrder(2)
                .content("add_content")
                .build();
        em.flush();
        em.clear();

        //then
        Question findQuestion = em.find(Question.class, question.getId());
        assertThat(findQuestion.getOptions().size()).isEqualTo(2);
        assertThat(findQuestion.getOptions()).extracting("content")
                .containsExactly("test_content", "add_content");
        assertThat(findQuestion.getOptions()).extracting("optionOrder")
                .containsExactly(1, 2);
    }
}