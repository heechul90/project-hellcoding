package com.heech.hellcoding.core.shop.item.album.domain;

import com.heech.hellcoding.core.shop.ShopTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ShopTestConfig.class)
class AlbumTest {

    @PersistenceContext EntityManager em;

    @Test
    public void createAlbumTest() {
        //given

        //when

        //then
    }

}