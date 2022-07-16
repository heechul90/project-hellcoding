package com.heech.hellcoding.core.category;

import com.heech.hellcoding.core.category.repository.CategoryQueryRepository;
import com.heech.hellcoding.core.category.repository.CategoryRepository;
import com.heech.hellcoding.core.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class CategoryTestConfig {

    @PersistenceContext EntityManager em;

    @Autowired CategoryRepository categoryRepository;

    @Bean
    public CategoryQueryRepository categoryQueryRepository() {
        return new CategoryQueryRepository(em);
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryService(categoryRepository, categoryQueryRepository());
    }
}
