package com.heech.hellcoding.api.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heech.hellcoding.api.category.request.CreateCategoryRequest;
import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.category.service.CategoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(ApiCategoryController.class)
class ApiCategoryControllerTest {

    @Autowired MockMvc mockMvc;

    @MockBean CategoryService categoryService;

    @Autowired ObjectMapper objectMapper;

    @Test
    void findCategories() {
    }

    @Test
    void findCategory() throws Exception {
        //given
        Category category = Category.createCategoryBuilder()
                .serviceName(ServiceName.SHOP)
                .name("parent_name")
                .content("parent_content")
                .serialNumber(1)
                .build();
        Category child = Category.createCategoryBuilder()
                .parent(category)
                .serviceName(ServiceName.SHOP)
                .name("child_name")
                .content("child_content")
                .serialNumber(1)
                .build();
        given(categoryService.findCategory(any())).willReturn(category);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/{id}", 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("searchServiceName", "SHOP")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.serviceName").value(ServiceName.SHOP.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.categoryName").value("parent_name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.categoryContent").value("parent_content"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.categorySerialNumber").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.childCategories").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.childCategories").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.childCategories.length()", Matchers.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void saveCategory() throws Exception {
        //given
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setServiceName(ServiceName.SHOP);
        request.setCategorySerialNumber(1);
        request.setCategoryName("test_name");
        request.setCategoryContent("test_content");

        Category category = Category.createCategoryBuilder()
                .serviceName(ServiceName.SHOP)
                .serialNumber(1)
                .name("test_name")
                .content("test_content")
                .build();
        given(categoryService.saveCategory(category)).willReturn(category.getId());

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.savedCategoryId").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }
}