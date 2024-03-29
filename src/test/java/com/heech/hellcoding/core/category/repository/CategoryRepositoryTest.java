package com.heech.hellcoding.core.category.repository;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired CategoryRepository categoryRepository;

    private Category getCategory(Category parent, ServiceName serviceName, int serialNumber, String name, String content) {
        Category category = Category.createCategoryBuilder()
                .parent(parent)
                .serviceName(serviceName)
                .serialNumber(serialNumber)
                .name(name)
                .content(content)
                .build();
        categoryRepository.save(category);
        return category;
    }

    @Test
    public void saveTest() throws Exception{
        //given
        Category bookCategory = getCategory(null, ServiceName.SHOP, 0, "book", "book");
        Category albumCategory = getCategory(null, ServiceName.SHOP, 1, "album", "album");
        Category movieCategory = getCategory(null, ServiceName.SHOP, 2, "movie", "movie");
        Category javaCategory = getCategory(bookCategory, ServiceName.SHOP, 0, "java", "java");

        //when

        //then
        assertThat(bookCategory).isEqualTo(bookCategory);
        assertThat(bookCategory.getParent()).isNull();
        assertThat(bookCategory.getServiceName()).isEqualTo(ServiceName.SHOP);
        assertThat(bookCategory.getSerialNumber()).isEqualTo(0);
        assertThat(bookCategory.getName()).isEqualTo("book");
        assertThat(bookCategory.getContent()).isEqualTo("book");
    }

    @Test
    public void deleteTest() throws Exception{
        //given
        Category bookCategory = getCategory(null, ServiceName.SHOP, 0, "book", "book");
        Category albumCategory = getCategory(null, ServiceName.SHOP, 1, "album", "album");
        Category movieCategory = getCategory(null, ServiceName.SHOP, 2, "movie", "movie");
        Category javaCategory = getCategory(bookCategory, ServiceName.SHOP, 0, "java", "java");

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