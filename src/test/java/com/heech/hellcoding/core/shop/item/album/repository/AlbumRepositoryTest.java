package com.heech.hellcoding.core.shop.item.album.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlbumRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired AlbumRepository albumRepository;

    @Test
    public void saveAlbumTest() {
        //given

        //when

        //then
    }
}