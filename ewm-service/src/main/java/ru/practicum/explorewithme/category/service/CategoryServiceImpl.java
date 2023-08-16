package ru.practicum.explorewithme.category.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.category.exception.CategoryNotFoundException;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;

import java.util.List;

import static ru.practicum.explorewithme.category.dto.CategoryMapper.toCategory;
import static ru.practicum.explorewithme.category.dto.CategoryMapper.toDto;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository repository;

    @Override
    public CategoryDto create(NewCategoryDto newCategory) {
        Category savedCategory = repository.save(toCategory(newCategory));

        log.info("Saved category: " + savedCategory);

        return toDto(savedCategory);
    }

    @Override
    public CategoryDto delete(long id) {
        Category category = findCategory(id);
        repository.delete(category);

        log.info("Category: " + category + " deleted");

        return toDto(category);
    }

    @Override
    public CategoryDto update(long id, CategoryDto categoryDto) {
        Category category = findCategory(id);
        updateCategoryFields(category, categoryDto);
        repository.save(category);

        log.info("Category: " + category + " updated");

        return toDto(category);
    }

    @Override
    public CategoryDto get(long id) {
        Category category = findCategory(id);

        log.info("Found category: " + category);

        return toDto(category);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Category> categories = repository.findAllBy(pageRequest);

        log.info("Found categories: " + categories);

        return toDto(categories);
    }

    private void updateCategoryFields(Category category, CategoryDto categoryDto) {
        if (categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }
    }

    private Category findCategory(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}