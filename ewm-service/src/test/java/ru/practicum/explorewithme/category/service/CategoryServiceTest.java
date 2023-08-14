package ru.practicum.explorewithme.category.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.category.exception.CategoryNotFoundException;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static ru.practicum.explorewithme.category.dto.CategoryMapper.toCategory;
import static ru.practicum.explorewithme.category.dto.CategoryMapper.toDto;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service = new CategoryServiceImpl();

    private long categoryId;
    private Category category;
    private NewCategoryDto newCategoryDto;

    @BeforeEach
    public void setUp() {
        categoryId = 1L;

        category = Category.builder()
                .id(categoryId)
                .name("Драма")
                .build();

        newCategoryDto = NewCategoryDto.builder()
                .name("Драма")
                .build();
    }

    @Test
    public void createWhenMethodInvokeReturnCategory() {
        when(repository.save(toCategory(newCategoryDto))).thenReturn(category);

        CategoryDto categoryDto = service.create(newCategoryDto);

        assertEquals(toDto(category), categoryDto);
    }

    @Test
    public void deleteWhenMethodInvokeReturnCategory() {
        when(repository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDto categoryDto = service.delete(categoryId);

        assertEquals(toDto(category), categoryDto);
    }

    @Test
    public void updateWhenMethodInvokeReturnCategory() {
        when(repository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDto categoryDto = service.update(categoryId, toDto(category));

        assertEquals(toDto(category), categoryDto);
    }

    @Test
    public void getWhenMethodInvokeReturnCategory() {
        when(repository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDto categoryDto = service.get(categoryId);

        assertEquals(toDto(category), categoryDto);
    }

    @Test
    public void getWhenCategoryNotFoundThrowException() {
        when(repository.findById(categoryId)).thenThrow(CategoryNotFoundException.class);

        assertThrows(CategoryNotFoundException.class, () -> service.get(categoryId));
    }

    @Test
    public void getCategoriesWhenMethodInvokeReturnCategories() {
        when(repository.findAllBy(PageRequest.of(0, 10))).thenReturn(List.of(category));

        List<CategoryDto> categoryDto = service.getCategories(0, 10);

        assertEquals(List.of(toDto(category)), categoryDto);
    }
}