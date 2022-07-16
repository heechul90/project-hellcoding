package com.heech.hellcoding.core.category.repository;

import com.heech.hellcoding.core.category.CategoryTestConfig;
import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.category.dto.CategorySearchCondition;
import com.heech.hellcoding.core.common.dto.SearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(CategoryTestConfig.class)
public class CategoryQueryRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired CategoryQueryRepository categoryQueryRepository;

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
    public void findCategoriesTest() {
        //given
        Category bookCategory = getCategory(null, ServiceName.SHOP, 0, "book", "book");
        Category albumCategory = getCategory(null, ServiceName.SHOP, 1, "album", "album");
        Category developCategory = getCategory(bookCategory, ServiceName.SHOP, 0, "develop", "develop");
        Category languageCategory = getCategory(bookCategory, ServiceName.SHOP, 1, "language", "language");
        Category kpopCategory = getCategory(albumCategory, ServiceName.SHOP, 0, "k-pop", "k-pop");
        Category balladCategory = getCategory(albumCategory, ServiceName.SHOP, 1, "ballad", "ballad");

        //when
        CategorySearchCondition condition = new CategorySearchCondition();
        condition.setSearchServiceName(ServiceName.SHOP);
        condition.setSearchCondition(SearchCondition.NAME);
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);

        Page<Category> content = categoryQueryRepository.findCategories(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(6);
    }
}
