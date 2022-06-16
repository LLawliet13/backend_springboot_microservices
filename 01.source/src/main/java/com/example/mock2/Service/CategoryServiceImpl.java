package com.example.mock2.Service;

import com.example.mock2.DTO.CategoryDTO;
import com.example.mock2.Repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private static final int NUMBER_OF_ENTITY_PER_PAGE = 10;

    private CategoryRepository categoryRepository;

    @Override
    public Page<CategoryDTO> findAllCategory(int pageNumber) {
        return null;
    }

    @Override
    public Set<CategoryDTO> findAllCategory() {
        return null;
    }

    @Override
    public CategoryDTO updateACategory(int id, CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public CategoryDTO addACategory(CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public CategoryDTO deleteACategory(int id) {
        return null;
    }

    @Override
    public Set<CategoryDTO> findByName(String name) {
        return null;
    }

    @Override
    public Page<CategoryDTO> findByName(String name, int pageNumber) {
        return null;
    }
}
