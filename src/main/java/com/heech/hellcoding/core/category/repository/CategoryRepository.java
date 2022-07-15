package com.heech.hellcoding.core.category.repository;

import com.heech.hellcoding.core.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
