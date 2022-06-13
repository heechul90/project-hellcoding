package com.heech.hellcoding.core.shop.category.repository;

import com.heech.hellcoding.core.shop.category.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CategoryRepository categoryRepository;

    private Category getCategory(String name, String title, String content) {
        Category bookCategory = Category.createCategoryBuilder()
                .name(name)
                .title(title)
                .content(content)
                .build();
        return bookCategory;
    }

    @Test
    public void findCategoriesTest() throws Exception{
        //given

        //when

        //then
    }

    @Test
    public void saveTest() throws Exception{
        //given
        Category bookCategory = getCategory("도서", "도서카테고리", "도서관련 카테고리이니다.");

        //when
        Category savedCategory = categoryRepository.save(bookCategory);

        //then
        assertThat(savedCategory).isEqualTo(bookCategory);
        assertThat(savedCategory.getName()).isEqualTo("도서");
        assertThat(savedCategory.getTitle()).isEqualTo("도서카테고리");
        assertThat(savedCategory.getContent()).isEqualTo("도서관련 카테고리이니다.");
    }

}