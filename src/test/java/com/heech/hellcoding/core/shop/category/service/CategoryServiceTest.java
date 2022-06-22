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

    private Category getRootCategory(String name, Integer order) {
        Category rootCategory = Category.createRootCategoryBuilder()
                .name(name)
                .categoryOrder(order)
                .build();
        em.persist(rootCategory);
        return rootCategory;
    }

    private Category getChildCategory(Category parent, String name, Integer order) {
        Category childCategory = Category.createChildCategoryBuilder()
                .parent(parent)
                .name(name)
                .categoryOrder(order)
                .build();
        em.persist(childCategory);
        return childCategory;
    }

    @Test
    void findCategoriesTest() {
        //given
        Category bookCategory = getRootCategory("도서", 1);
        Category albumCategory = getRootCategory("음반", 2);
        Category developCategory = getChildCategory(bookCategory, "개발", 1);
        Category languageCategory = getChildCategory(bookCategory, "언어", 2);
        Category kpopCategory = getChildCategory(albumCategory, "케이팝", 1);
        Category balladCategory = getChildCategory(albumCategory, "발라드", 2);

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
        Category bookCategory = getRootCategory("도서", 1);

        //when
        Category findCategory = categoryService.findCategory(bookCategory.getId());

        //then
        assertThat(findCategory.getName()).isEqualTo("도서");
    }

    @Test
    void saveCategoryTest() {
        //given
        Category bookCategory = getRootCategory("도서", 1);

        //when
        Long savedId = categoryService.saveCategory(bookCategory);

        //then
        Category findCategory = categoryService.findCategory(savedId);
        assertThat(findCategory.getName()).isEqualTo("도서");
    }


    @Test
    void updateCategoryTest() {
        //given
        Category bookCategory = getRootCategory("도서", 1);
        em.flush();
        em.clear();

        //when
        categoryService.updateCategory(bookCategory.getId(), "호미", 5);
        em.flush();
        em.clear();

        //then
        Category findCategory = categoryService.findCategory(bookCategory.getId());
        assertThat(findCategory.getName()).isEqualTo("호미");
        assertThat(findCategory.getCategoryOrder()).isEqualTo(5);
    }

    @Test
    public void activateCategoryTest() throws Exception{
        //given
        Category bookCategory = getRootCategory("도서", 1);
        em.flush();
        em.clear();

        //when
        categoryService.activateCategory(bookCategory.getId());
        em.flush();
        em.clear();

        //then
        Category findCategory = categoryService.findCategory(bookCategory.getId());
        assertThat(findCategory.getActivation()).isEqualTo("Y");
    }

    @Test
    public void deactivateCategoryTest() throws Exception{
        //given
        Category bookCategory = getRootCategory("도서", 1);
        em.flush();
        em.clear();

        //when
        categoryService.deactivateCategory(bookCategory.getId());
        em.flush();
        em.clear();

        //then
        Category findCategory = categoryService.findCategory(bookCategory.getId());
        assertThat(findCategory.getActivation()).isEqualTo("N");
    }

    @Test
    void deleteCategoryTest() {
        //given
        Category bookCategory = getRootCategory("도서", 1);
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