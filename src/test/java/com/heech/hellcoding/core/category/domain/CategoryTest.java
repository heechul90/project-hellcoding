package com.heech.hellcoding.core.category.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryTest {

    @PersistenceContext
    EntityManager em;

    private Category getCategory(Category parent, ServiceName serviceName, int serialNumber, String name, String content) {
        Category category = Category.createCategoryBuilder()
                .parent(parent)
                .serviceName(serviceName)
                .serialNumber(serialNumber)
                .name(name)
                .content(content)
                .build();
        em.persist(category);
        return category;
    }

    @Test
    public void createCategoryTest() {
        //given
        Category bookCategory = getCategory(null, ServiceName.SHOP, 0, "book", "book");
        Category albumCategory = getCategory(null, ServiceName.SHOP, 1, "album", "album");
        Category movieCategory = getCategory(null, ServiceName.SHOP, 2, "movie", "movie");
        Category javaCategory = getCategory(bookCategory, ServiceName.SHOP, 0, "java", "java");

        //when

        //then
        assertThat(bookCategory.getChildren().get(0)).isEqualTo(javaCategory);
        assertThat(javaCategory.getParent()).isEqualTo(bookCategory);

        List<Category> resultList =
                em.createQuery("select c from Category c where c.parent is null", Category.class).getResultList();
        assertThat(resultList.size()).isEqualTo(3);
        assertThat(resultList).extracting("name").contains("book", "album", "movie");
        assertThat(resultList).extracting("serialNumber").contains(0, 1, 2);
    }

    @Test
    public void updateCategorTest() {
        //given
        Category bookCategory = getCategory(null, ServiceName.SHOP, 0, "book", "book");
        Category albumCategory = getCategory(null, ServiceName.SHOP, 1, "album", "album");
        Category movieCategory = getCategory(null, ServiceName.SHOP, 2, "movie", "movie");
        Category javaCategory = getCategory(bookCategory, ServiceName.SHOP, 0, "java", "java");

        //when
        Category findCategory = em.find(Category.class, javaCategory.getId());
        findCategory.updateCategoryBuilder()
                .parent(movieCategory)
                .serviceName(ServiceName.SHOP)
                .serialNumber(10)
                .name("update_java")
                .content("update_java")
                .build();

        //then
        assertThat(findCategory.getParent()).isEqualTo(movieCategory);
        assertThat(findCategory.getParent().getChildren().get(0)).isEqualTo(findCategory);
        em.flush();
        em.clear();

        Category updatedCategory = em.find(Category.class, javaCategory.getId());
        assertThat(updatedCategory.getParent().getName()).isEqualTo("movie");
        assertThat(updatedCategory.getSerialNumber()).isEqualTo(10);
        assertThat(updatedCategory.getName()).isEqualTo("update_java");
        assertThat(updatedCategory.getContent()).isEqualTo("update_java");
    }
}