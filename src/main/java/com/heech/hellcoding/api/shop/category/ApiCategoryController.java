package com.heech.hellcoding.api.shop.category;

import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.shop.category.domain.Category;
import com.heech.hellcoding.core.shop.category.dto.CategoryDto;
import com.heech.hellcoding.core.shop.category.dto.CategorySearchCondition;
import com.heech.hellcoding.core.shop.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/categories")
public class ApiCategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록 조회
     */
    @GetMapping
    public JsonResult findCategories(CategorySearchCondition condition, Pageable Pageable) {
        Page<Category> content = categoryService.findCategories(condition, Pageable);
        List<CategoryDto> collect = content.getContent().stream()
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getCategoryOrder(),
                        category.getActivation(),
                        category.getParent() != null ? new CategoryDto(category.getParent().getId(), category.getParent().getName(), category.getParent().getCategoryOrder()) : null,
                        category.getCreatedDate(),
                        category.getCreatedBy()
                ))
                .collect(Collectors.toList());
        return JsonResult.OK(collect);
    }

    /**
     * 카테고리 단건 조회
     */
    @GetMapping(value = "/{id}")
    public JsonResult findCategory(@PathVariable("id") Long categoryId) {
        Category findCategory = categoryService.findCategory(categoryId);
        CategoryDto category = new CategoryDto(
                findCategory.getId(),
                findCategory.getName(),
                findCategory.getCategoryOrder(),
                findCategory.getActivation(),
                findCategory.getParent() != null ? new CategoryDto(
                        findCategory.getParent().getId(),
                        findCategory.getParent().getName(),
                        findCategory.getParent().getCategoryOrder()
                ) : null,
                findCategory.getCreatedDate(),
                findCategory.getCreatedBy()
        );
        return JsonResult.OK(category);
    }
}
