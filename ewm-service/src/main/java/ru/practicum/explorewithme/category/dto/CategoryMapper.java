package ru.practicum.explorewithme.category.dto;

import ru.practicum.explorewithme.category.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        Long id = category.getId();
        String name = category.getName();

        return new CategoryDto(id, name);
    }

    public static Category toCategory(NewCategoryDto category) {
        String name = category.getName();

        return new Category(null, name);
    }

    public static Category toCategory(CategoryDto category) {
        Long id = category.getId();
        String name = category.getName();

        return new Category(id, name);
    }

    public static List<Category> toCategory(List<CategoryDto> categories) {
        return categories.stream()
                .map(CategoryMapper::toCategory)
                .collect(Collectors.toList());
    }

    public static List<CategoryDto> toDto(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }
}