package com.heech.hellcoding.core.survey.option.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.survey.option.domain.Option;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OptionServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OptionService optionService;

    private Option getOption() {
        Option option = Option.createOptionBuilder()
                .optionOrder(1)
                .content("test_option_content")
                .build();
        return option;
    }

    @Test
    public void findOptionTest() throws Exception{
        //given
        Option option = getOption();
        em.persist(option);
        em.flush();
        em.clear();

        //when
        Option findOption = optionService.findOption(option.getId());

        //then
        assertThat(findOption.getOptionOrder()).isEqualTo(1);
        assertThat(findOption.getContent()).isEqualTo("test_option_content");
    }

    @Test
    void deleteOptionTest() {
        //given
        Option option = getOption();
        em.persist(option);
        em.flush();
        em.clear();

        //when
        optionService.deleteOption(option.getId());

        //then
        assertThatThrownBy(() -> optionService.findOption(option.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("잘못된");
    }
}