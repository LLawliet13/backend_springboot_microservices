package com.example.mock2.Service;

import com.example.mock2.DTO.CategoryDTO;
import com.example.mock2.Entity.Category;
import com.example.mock2.Repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private static final int NUMBER_OF_ENTITY_PER_PAGE = 10;

    private CategoryRepository categoryRepository;

    @Override
    public Page<CategoryDTO> findAllCategory(int pageNumber) {
        return categoryRepository.findAll(PageRequest.of(pageNumber,NUMBER_OF_ENTITY_PER_PAGE)).map(
                category -> category.convertToCategoryDTO()
        );
    }

    @Override
    public List<CategoryDTO> findAllCategory() {
        return categoryRepository.findAll().stream().map(
                category -> category.convertToCategoryDTO()
        ).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateACategory(long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).get();
        category.setCategoryId(id);
        category.setCategoryName(category.getCategoryName());
        categoryRepository.save(category);
        categoryDTO.setCategoryId(id);
        return categoryDTO;
    }

    @Override
    public CategoryDTO addACategory(CategoryDTO categoryDTO) {
        categoryRepository.save(categoryDTO.convertToCategory());
        Category category = categoryRepository.findByCategoryName(categoryDTO.getCategoryName()).get(0);
        return category.convertToCategoryDTO();
    }

    @Override
    public CategoryDTO deleteACategory(long id) {

        Category category = categoryRepository.findById(id).get();
        return category.convertToCategoryDTO();
    }

    @Override
    public List<CategoryDTO> findByName(String name) {
        return categoryRepository.findByCategoryName(name).stream().map(
                category -> category.convertToCategoryDTO()
        ).collect(Collectors.toList());
    }

    @Override
    public Page<CategoryDTO> findByName(String name, int pageNumber) {
        return categoryRepository.findByCategoryName(name,PageRequest.of(pageNumber,NUMBER_OF_ENTITY_PER_PAGE)).map(
                category -> category.convertToCategoryDTO()
        );
    }
}
