package com.heech.hellcoding.core.shop.category.repository;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.category.domain.Category;
import com.heech.hellcoding.core.shop.category.dto.CategorySearchCondition;
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

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CategoryRepository categoryRepository;

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
    public void findCategoriesTest() throws Exception{
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
        Page<Category> content = categoryRepository.findCategories(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(6);
    }

    @Test
    public void saveTest() throws Exception{
        //given
        Category bookCategory = new Category("도서", 1);

        //when
        Category savedCategory = categoryRepository.save(bookCategory);

        //then
        assertThat(savedCategory).isEqualTo(bookCategory);
        assertThat(savedCategory.getName()).isEqualTo("도서");
    }

    @Test
    public void updateTest() throws Exception{
        //given
        Category bookCategory = getRootCategory("도서", 1);
        em.flush();
        em.clear();

        //when
        Category findCategory = categoryRepository.findById(bookCategory.getId()).orElse(null);
        findCategory.updateCategoryBuilder()
                .name("도서1")
                .categoryOrder(5)
                .build();
        em.flush();
        em.clear();

        //then
        Category updatedCategory = categoryRepository.findById(findCategory.getId()).orElse(null);
        assertThat(updatedCategory.getName()).isEqualTo("도서1");
        assertThat(updatedCategory.getCategoryOrder()).isEqualTo(5);
    }

    @Test
    public void deleteTest() throws Exception{
        //given
        Category bookCategory = getRootCategory("도서", 1);

        //when
        Category findCategory = categoryRepository.findById(bookCategory.getId()).orElse(null);
        categoryRepository.delete(findCategory);

        //then
        assertThatThrownBy(() -> categoryRepository.findById(bookCategory.getId())
                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다.")))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("잘못된");
    }

}