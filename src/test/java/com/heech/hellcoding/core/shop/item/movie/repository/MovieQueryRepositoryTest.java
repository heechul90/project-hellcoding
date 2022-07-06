package com.heech.hellcoding.core.shop.item.movie.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MovieQueryRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MovieQueryRepository movieQueryRepository;

    @Test
    void findMoviesTest() {
        //given

        //when

        //then
    }
}