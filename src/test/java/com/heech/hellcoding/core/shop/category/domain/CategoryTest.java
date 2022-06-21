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

    private Category getRootCategory(String name, int order) {
        return Category.createRootCategoryBuilder()
                .name(name)
                .categoryOrder(order)
                .build();
    }

    private Category getChildCategory(Category parent, String name, int order) {
        return Category.createChildCategoryBuilder()
                .parent(parent)
                .name(name)
                .categoryOrder(order)
                .build();
    }

    @Test
    public void createRootCategoryTest() throws Exception{
        //given
        Category bookCategory = getRootCategory("도서", 1);
        Category albumCategory = getRootCategory("음반", 2);
        Category movieCategory = getRootCategory("영화", 3);

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
    public void createChildCategoryTest() throws Exception{
        //given
        Category bookCategory = getRootCategory("도서", 1);
        Category albumCategory = getRootCategory("음반", 2);
        Category movieCategory = getRootCategory("영화", 3);
        em.persist(bookCategory);
        em.persist(albumCategory);
        em.persist(movieCategory);

        //when
        Category itCategory = getChildCategory(bookCategory, "IT", 1);
        em.persist(itCategory);

        //then
        assertThat(itCategory.getParent().getName()).isEqualTo("도서");
    }

}