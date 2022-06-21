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

    private Category getRootCategory(String name, Integer order) {
        return Category.createRootCategoryBuilder()
                .name(name)
                .categoryOrder(order)
                .build();
    }

    private Category getChildCategory(Category parent, String name, Integer order) {
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
        assertThat(resultList).extracting("categoryOrder").containsExactly(1, 2, 3);
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
        Category findCategory = em.find(Category.class, itCategory.getId());
        assertThat(findCategory.getParent()).isEqualTo(bookCategory);
        assertThat(findCategory.getParent().getName()).isEqualTo("도서");
        assertThat(findCategory.getParent().getCategoryOrder()).isEqualTo(1);
    }

    @Test
    public void updateCategorTest() throws Exception{
        //given
        Category bookCategory = getRootCategory("도서", 1);
        em.persist(bookCategory);
        em.flush();
        em.clear();

        //when
        Category findCategory = em.find(Category.class, bookCategory.getId());
        findCategory.updateCategoryBuilder()
                .name("BOOK")
                .categoryOrder(5)
                .build();
        em.flush();
        em.clear();

        //then
        Category updatedCategory = em.find(Category.class, bookCategory.getId());
        assertThat(updatedCategory.getName()).isEqualTo("BOOK");
        assertThat(updatedCategory.getCategoryOrder()).isEqualTo(5);
    }

    @Test
    public void activateCategoryTest() throws Exception {
        //given
        Category bookCategory = getRootCategory("도서", 1);
        em.persist(bookCategory);
        em.flush();
        em.clear();

        //when //then
        Category findCategory = em.find(Category.class, bookCategory.getId());
        assertThat(findCategory.getActivationAt()).isEqualTo("Y");

        findCategory.deactivateCategory();
        assertThat(findCategory.getActivationAt()).isEqualTo("N");

        findCategory.activateCategory();
        assertThat(findCategory.getActivationAt()).isEqualTo("Y");
    }
}