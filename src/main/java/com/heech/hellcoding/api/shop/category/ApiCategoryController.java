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

    @GetMapping
    public JsonResult findCategories(CategorySearchCondition condition, Pageable Pageable) {
        Page<Category> content = categoryService.findCategories(condition, Pageable);

        List<CategoryDto> collect = content.getContent().stream()
                .collect(
                        Collectors.groupingBy(
                                category -> new CategoryDto(category.getId(), category.getName()),
                                Collectors.mapping(category -> new CategoryDto(category.getId(), category.getName()), Collectors.toList())
                        )).entrySet().stream()
                .map(e -> new CategoryDto(e.getKey().getCategoryId(), e.getKey().getCategoryName(), e.getValue()))
                .collect(Collectors.toList());

        /*List<CategoryDto> collect = content.getContent().stream()
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getTitle(),
                        category.getContent()
                ))
                .collect(Collectors.toList());*/
        return JsonResult.OK(collect);
    }
}
