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

    private Category getCategory(String name, String title, String content) {
        Category category = Category.createCategoryBuilder()
                .name(name)
                .title(title)
                .content(content)
                .build();
        em.persist(category);
        return category;
    }

    private Category getChildCategory(String name, String title, String content, Category bookCategory) {
        Category childCategory = new Category(name, title, content, bookCategory);
        em.persist(childCategory);
        return childCategory;
    }

    @Test
    public void findCategoriesTest() throws Exception{
        //given
        Category bookCategory = getCategory("도서", "도서카테고리", "도서관련 카테고리이니다.");
        Category albumCategory = getCategory("음반", "음반카테고리", "음반관련 카테고리이니다.");
        Category developCategory = getChildCategory("개발", "개발카테고리", "개발관련 카테고리입니다.", bookCategory);
        Category languageCategory = getChildCategory("언어", "언어카테고리", "언어관련 카테고리입니다.", bookCategory);
        Category kpopCategory = getChildCategory("케이팝", "케이팝카테고리", "케이팝관련 카테고리입니다.", albumCategory);
        Category balladCategory = getChildCategory("발라드", "발라드카테고리", "발라드관련 카테고리입니다.", albumCategory);

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
        Category bookCategory = new Category("도서", "도서카테고리", "도서관련 카테고리이니다.");

        //when
        Category savedCategory = categoryRepository.save(bookCategory);

        //then
        assertThat(savedCategory).isEqualTo(bookCategory);
        assertThat(savedCategory.getName()).isEqualTo("도서");
        assertThat(savedCategory.getTitle()).isEqualTo("도서카테고리");
        assertThat(savedCategory.getContent()).isEqualTo("도서관련 카테고리이니다.");
    }

    @Test
    public void updateTest() throws Exception{
        //given
        Category bookCategory = getCategory("도서", "도서카테고리", "도서관련 카테고리이니다.");
        em.flush();
        em.clear();

        //when
        Category findCategory = categoryRepository.findById(bookCategory.getId()).orElse(null);
        findCategory.updateCategoryBuilder()
                .name("도서1")
                .title("도서카테고리1")
                .content("도서관련 카테고리이니다.1")
                .build();
        em.flush();
        em.clear();

        //then
        Category updatedCategory = categoryRepository.findById(findCategory.getId()).orElse(null);
        assertThat(updatedCategory.getName()).isEqualTo("도서1");
        assertThat(updatedCategory.getTitle()).isEqualTo("도서카테고리1");
        assertThat(updatedCategory.getContent()).isEqualTo("도서관련 카테고리이니다.1");
    }

    @Test
    public void deleteTest() throws Exception{
        //given
        Category bookCategory = getCategory("도서", "도서카테고리", "도서관련 카테고리이니다.");

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