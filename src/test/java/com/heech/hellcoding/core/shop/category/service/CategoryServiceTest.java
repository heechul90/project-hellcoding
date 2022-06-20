package com.heech.hellcoding.core.shop.category.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.category.domain.Category;
import com.heech.hellcoding.core.shop.category.dto.CategorySearchCondition;
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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CategoryService categoryService;

    private Category getCategory(String name) {
        Category category = Category.createRootCategoryBuilder()
                .name(name)
                .build();
        em.persist(category);
        return category;
    }

    private Category getChildCategory(String name, String title, String content, Category bookCategory) {
        Category childCategory = new Category(bookCategory, name, 1);
        em.persist(childCategory);
        return childCategory;
    }

    @Test
    void findCategoriesTest() {
        //given
        Category bookCategory = getCategory("도서");
        Category albumCategory = getCategory("음반");
        Category developCategory = getChildCategory("개발", "개발카테고리", "개발관련 카테고리입니다.", bookCategory);
        Category languageCategory = getChildCategory("언어", "언어카테고리", "언어관련 카테고리입니다.", bookCategory);
        Category kpopCategory = getChildCategory("케이팝", "케이팝카테고리", "케이팝관련 카테고리입니다.", albumCategory);
        Category balladCategory = getChildCategory("발라드", "발라드카테고리", "발라드관련 카테고리입니다.", albumCategory);

        //when
        CategorySearchCondition condition = new CategorySearchCondition();
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        Page<Category> content = categoryService.findCategories(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(6);
    }

    @Test
    void findCategoryTest() {
        //given
        Category bookCategory = getCategory("도서");

        //when
        Category findCategory = categoryService.findCategory(bookCategory.getId());

        //then
        assertThat(findCategory.getName()).isEqualTo("도서");
    }

    @Test
    void saveCategoryTest() {
        //given
        Category bookCategory = getCategory("도서");

        //when
        Long savedId = categoryService.saveCategory(bookCategory);

        //then
        Category findCategory = categoryService.findCategory(savedId);
        assertThat(findCategory.getName()).isEqualTo("도서");
    }


    @Test
    void updateCategoryTest() {
        //given
        Category bookCategory = getCategory("도서");
        em.flush();
        em.clear();

        //when
        categoryService.updateCategory(bookCategory.getId(), "호미", "호미", "호미");

        //then
        Category findCategory = categoryService.findCategory(bookCategory.getId());
        assertThat(findCategory.getName()).isEqualTo("호미");
    }

    @Test
    void deleteCategoryTest() {
        //given
        Category bookCategory = getCategory("도서");
        em.flush();
        em.clear();

        //when
        categoryService.deleteCategory(bookCategory.getId());

        //then
        assertThatThrownBy(() -> categoryService.findCategory(bookCategory.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("잘못된");
    }

}