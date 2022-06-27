package com.heech.hellcoding.core.survey.option.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class OptionRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void saveOptionTest() throws Exception{
        //given
        System.out.println("test start!");

        //when

        //then
    }

}