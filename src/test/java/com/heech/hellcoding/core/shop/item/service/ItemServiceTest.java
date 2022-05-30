package com.heech.hellcoding.core.shop.item.service;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.shop.item.domain.Item;
import com.heech.hellcoding.core.shop.item.domain.Movie;
import com.heech.hellcoding.core.shop.item.dto.ItemSearchCondition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemService itemService;

    @Test
    void findItems() {
        //given
        Movie movie = Movie.builder()
                .director("show box")
                .actor("마동석")
                .build();
        movie.createItem(
                "범죄도시2",
                "베트남에 가다",
                "베트남에서 범인을 잡앗다",
                15000,
                15
        );
        itemService.saveItem(movie);
        em.flush();
        em.clear();

        //when
        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("도시");

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Item> content = itemService.findItems(condition, pageRequest);

        //then
        assertThat(content.getContent()).extracting("name").containsExactly("범죄도시2");
    }

    @Test
    void save() {
        //given
        Movie movie = Movie.builder()
                .director("show box")
                .actor("마동석")
                .build();
        movie.createItem(
                "범죄도시2",
                "베트남에 가다",
                "베트남에서 범인을 잡앗다",
                15000,
                15
        );

        //when
        Movie savedMovie = (Movie) itemService.saveItem(movie);
        em.flush();
        em.clear();

        //then
        Movie findMovie = (Movie) itemService.findById(savedMovie.getId()).orElse(null);
        assertThat(findMovie.getActor()).isEqualTo("마동석");
    }

    @Test
    void findById() {
        //given
        Movie movie = Movie.builder()
                .director("show box")
                .actor("마동석")
                .build();
        movie.createItem(
                "범죄도시2",
                "베트남에 가다",
                "베트남에서 범인을 잡앗다",
                15000,
                15
        );
        Movie savedMovie = (Movie) itemService.saveItem(movie);
        em.flush();
        em.clear();

        //when
        Movie findMovie = (Movie) itemService.findById(savedMovie.getId()).orElse(null);

        //then
        assertThat(findMovie.getActor()).isEqualTo("마동석");
    }

    @Test
    void findByName() {
        //given
        Movie movie = Movie.builder()
                .director("show box")
                .actor("마동석")
                .build();
        movie.createItem(
                "범죄도시2",
                "베트남에 가다",
                "베트남에서 범인을 잡앗다",
                15000,
                15
        );
        Movie savedMovie = (Movie) itemService.saveItem(movie);
        em.flush();
        em.clear();

        //when
        List<Item> resultList = itemService.findByName("범죄도시2");

        //then
        assertThat(resultList).extracting("name").containsExactly("범죄도시2");
        assertThat(resultList).extracting("director").containsExactly("show box");
    }

}