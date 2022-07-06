package com.heech.hellcoding.core.shop.category.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.shop.category.domain.Category;
import com.heech.hellcoding.core.shop.category.dto.CategorySearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryQueryRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired CategoryQueryRepository categoryQueryRepository;

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
    void findCategories() {
        //given
        Category bookCategory = getRootCategory("test_category_query_repository_도서", 1);
        Category albumCategory = getRootCategory("test_category_query_repository_음반", 2);
        Category developCategory = getChildCategory(bookCategory, "test_category_query_repository_개발", 1);
        Category languageCategory = getChildCategory(bookCategory, "test_category_query_repository_언어", 2);
        Category kpopCategory = getChildCategory(albumCategory, "test_category_query_repository_케이팝", 1);
        Category balladCategory = getChildCategory(albumCategory, "test_category_query_repository_발라드", 2);

        //when
        CategorySearchCondition condition = new CategorySearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("test_category_query_repository");
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        Page<Category> content = categoryQueryRepository.findCategories(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(6);
    }
}