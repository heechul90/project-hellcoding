package com.heech.hellcoding.core.shop.category.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryTest {

    @PersistenceContext
    EntityManager em;

    private Category getCategory(String name) {
        Category bookCategory = Category.createRootCategoryBuilder()
                .name(name)
                .categoryOrder(1)
                .build();
        return bookCategory;
    }

    @Test
    public void createRootCategoryTest() throws Exception{
        //given
        Category bookCategory = getCategory("도서");
        Category albumCategory = getCategory("음반");
        Category movieCategory = getCategory("영화");

        //when
        em.persist(bookCategory);
        em.persist(albumCategory);
        em.persist(movieCategory);

        //then
        List<Category> resultList =
                em.createQuery("select c from Category c", Category.class).getResultList();
        assertThat(resultList).extracting("name").contains("도서", "음반", "영화");
    }

    @Test
    @Rollback(value = false)
    public void createChildCategoryTest() throws Exception{
        //given
        Category bookCategory = getCategory("도서");
        Category albumCategory = getCategory("음반");
        Category movieCategory = getCategory("영화");
        em.persist(bookCategory);
        em.persist(albumCategory);
        em.persist(movieCategory);

        //when
        Category itCategory = Category.createChildCategoryBuilder()
                .parent(bookCategory)
                .name("IT")
                .categoryOrder(1)
                .build();
        em.persist(itCategory);

        //then
        assertThat(itCategory.getParent().getName()).isEqualTo("도서");
    }

}