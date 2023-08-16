package ru.practicum.explorewithme.category.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.category.service.CategoryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController controller;

    private long categoryId;
    private CategoryDto category;
    private NewCategoryDto newCategoryDto;
    private List<CategoryDto> categories;

    @BeforeEach
    public void setUp() {
        categoryId = 1L;
        category = new CategoryDto();
        categories = List.of(category);

        newCategoryDto = NewCategoryDto.builder()
                .name("drama")
                .build();
    }

    @Test
    public void getCategoriesWhenMethodInvokeReturnCategories() {
        when(categoryService.getCategories(0, 10)).thenReturn(categories);

        assertEquals(categories, controller.getCategories(0, 10));
    }

    @Test
    public void getWhenMethodInvokeReturnCategory() {
        when(categoryService.get(1)).thenReturn(category);

        assertEquals(category, controller.get(1));
    }

    @Test
    public void createCategoryWhenMethodInvokeReturnCategory() {
        when(categoryService.create(newCategoryDto)).thenReturn(category);

        assertEquals(category, controller.createCategory(newCategoryDto));
    }

    @Test
    public void deleteCategoryWhenMethodInvokeReturnCategory() {
        when(categoryService.delete(categoryId)).thenReturn(category);

        assertEquals(category, controller.deleteCategory(categoryId));
    }

    @Test
    public void updateCategoryWhenMethodInvokeReturnCategory() {
        when(categoryService.update(categoryId, category)).thenReturn(category);

        assertEquals(category, controller.updateCategory(categoryId, category));
    }
}