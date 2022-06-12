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

    private Category getCategory(String name, String title, String content) {
        Category bookCategory = Category.createCategoryBuilder()
                .name(name)
                .title(title)
                .content(content)
                .build();
        return bookCategory;
    }

    @Test
    public void createCategoryTest() throws Exception{
        //given
        Category bookCategory = getCategory("도서", "도서카테고리", "도서관련 카테고리이니다.");
        Category albumCategory = getCategory("음반", "음반카테고리", "음반관련 카테고리이니다.");
        Category movieCategory = getCategory("영화", "영화카테고리", "영화관련 카테고리이니다.");

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
        Category bookCategory = getCategory("도서", "도서카테고리", "도서관련 카테고리이니다.");
        Category albumCategory = getCategory("음반", "음반카테고리", "음반관련 카테고리이니다.");
        Category movieCategory = getCategory("영화", "영화카테고리", "영화관련 카테고리이니다.");
        em.persist(bookCategory);
        em.persist(albumCategory);
        em.persist(movieCategory);

        //when
        Category itCategory = new Category("IT", "IT관련 도서", "IT관련 도서입니다.", bookCategory);
        em.persist(itCategory);

        //then
        assertThat(itCategory.getParent().getName()).isEqualTo("도서");
        assertThat(itCategory.getChild().size()).isZero();
        assertThat(bookCategory.getChild()).extracting("name").containsExactly("IT");
    }

}