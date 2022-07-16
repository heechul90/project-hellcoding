package com.heech.hellcoding.core.shop.item.album.service;

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
class AlbumServiceTest {

    @PersistenceContext EntityManager em;

    @Autowired AlbumService albumService;

    @Test
    void findAlbumsTest() {
    }

    @Test
    void findAlbumTest() {
    }

    @Test
    void saveAlbumTest() {
    }

    @Test
    void deleteAlbumTest() {
    }
}