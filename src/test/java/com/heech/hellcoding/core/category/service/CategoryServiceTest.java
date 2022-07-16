package com.heech.hellcoding.core.category.service;

import com.heech.hellcoding.core.category.CategoryTestConfig;
import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.category.dto.CategorySearchCondition;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(CategoryTestConfig.class)
class CategoryServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CategoryService categoryService;

    private Category getCategory(Category parent, ServiceName serviceName, int serialNumber, String name, String content) {
        Category category = Category.createCategoryBuilder()
                .parent(parent)
                .serviceName(serviceName)
                .serialNumber(serialNumber)
                .name(name)
                .content(content)
                .build();
        categoryService.saveCategory(category);
        return category;
    }

    @Test
    void findCategoriesTest() {
        //given
        Category bookCategory = getCategory(null, ServiceName.SHOP, 0, "도서", "도서 카테고리");
        Category albumCategory = getCategory(null, ServiceName.SHOP, 1, "음반", "음반 카테고리");
        Category movieCategory = getCategory(null, ServiceName.SHOP, 2, "영화", "영화 카테고리");
        Category developCategory = getCategory(bookCategory, ServiceName.SHOP, 0, "개발", "개발 카테고리");
        Category languageCategory = getCategory(bookCategory, ServiceName.SHOP, 1, "언어", "언어 카테고리");
        Category kpopCategory = getCategory(albumCategory, ServiceName.SHOP, 0, "케이팝", "케이팝 카테고리");
        Category balladCategory = getCategory(albumCategory, ServiceName.SHOP, 1, "발라드", "발라드 카테고리");

        //when
        CategorySearchCondition condition = new CategorySearchCondition();
        condition.setSearchServiceName(ServiceName.SHOP);
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        Page<Category> content = categoryService.findCategories(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(7);
    }

    @Test
    void findCategoryTest() {
        //given
        Category bookCategory = getCategory(null, ServiceName.SHOP, 0, "도서", "도서 카테고리");

        //when
        Category findCategory = categoryService.findCategory(bookCategory.getId());

        //then
        assertThat(findCategory.getName()).isEqualTo("도서");
    }

    @Test
    void saveCategoryTest() {
        //given
        Category bookCategory = getCategory(null, ServiceName.SHOP, 0, "도서", "도서 카테고리");

        //when
        Long savedId = categoryService.saveCategory(bookCategory);

        //then
        Category findCategory = categoryService.findCategory(savedId);
        assertThat(findCategory.getName()).isEqualTo("도서");
    }


    @Test
    void updateCategoryTest() {
        //given
        Category bookCategory = getCategory(null, ServiceName.SHOP, 0, "도서", "도서 카테고리");
        em.flush();
        em.clear();

        //when
        categoryService.updateCategory(bookCategory.getId(), null, ServiceName.SHOP, 10, "update_도서", "update_도서 카테고리");
        em.flush();
        em.clear();

        //then
        Category findCategory = categoryService.findCategory(bookCategory.getId());
        assertThat(findCategory.getParent()).isNull();
        assertThat(findCategory.getServiceName()).isEqualTo(ServiceName.SHOP);
        assertThat(findCategory.getSerialNumber()).isEqualTo(10);
        assertThat(findCategory.getName()).isEqualTo("update_도서");
        assertThat(findCategory.getContent()).isEqualTo("update_도서 카테고리");
    }

    @Test
    void deleteCategoryTest() {
        //given
        Category bookCategory = getCategory(null, ServiceName.SHOP, 0, "도서", "도서 카테고리");
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