package com.heech.hellcoding.core.survey.option.repository;

import com.heech.hellcoding.core.survey.option.domain.Option;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OptionRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OptionRepository optionRepository;

    @Test
    public void saveOptionTest() {
        //given
        Option option = Option.createOptionBuilder()
                .optionOrder(1)
                .content("test_content")
                .build();

        //when
        Option savedOption = optionRepository.save(option);
        em.flush();
        em.clear();

        //then
        Option findOption = optionRepository.findById(savedOption.getId()).orElse(null);
        assertThat(findOption.getOptionOrder()).isEqualTo(1);
        assertThat(findOption.getContent()).isEqualTo("test_content");
    }

}