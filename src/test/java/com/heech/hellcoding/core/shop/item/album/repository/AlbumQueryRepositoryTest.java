package com.heech.hellcoding.core.shop.item.album.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
class AlbumQueryRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired AlbumQueryRepository albumQueryRepository;

    @Test
    void findAlbumsTest() {
        //given

        //when

        //then
    }
}