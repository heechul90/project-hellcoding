package com.heech.hellcoding.core.shop.item.movie.service;

import com.heech.hellcoding.core.shop.ShopTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ShopTestConfig.class)
class MovieServiceTest {

    @PersistenceContext EntityManager em;

    @Autowired MovieService movieService;

    @Test
    void findMoviesTest() {
    }

    @Test
    void findMovieTest() {
    }

    @Test
    void saveMovieTest() {
    }

    @Test
    void deleteMovieTest() {
    }
}