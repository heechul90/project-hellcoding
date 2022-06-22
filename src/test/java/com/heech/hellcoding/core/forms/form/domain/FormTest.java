package com.heech.hellcoding.core.forms.form.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class FormTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void createFormTest() throws Exception{
        //given
        Form form = Form.createFormBuilder()
                .title("test_title")
                .description("test_description")
                .period_at("Y")
                .beginDate(LocalDateTime.now())
                .endDate(LocalDateTime.now())
                .build();

        //when
        em.persist(form);
        em.flush();
        em.clear();

        //then
        Form findForm = em.find(Form.class, form.getId());
        assertThat(findForm.getTitle()).isEqualTo("test_title");
    }

}