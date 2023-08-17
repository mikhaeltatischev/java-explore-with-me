package ru.practicum.explorewithme.category.service;

import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto create(NewCategoryDto category);

    CategoryDto delete(long id);

    CategoryDto update(long id, CategoryDto category);

    CategoryDto get(long id);

    List<CategoryDto> getCategories(int from, int size);
}