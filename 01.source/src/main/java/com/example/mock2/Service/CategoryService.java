package com.example.mock2.Service;

import com.example.mock2.DTO.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface CategoryService {
    Page<CategoryDTO> findAllCategory(int pageNumber);
    Set<CategoryDTO> findAllCategory();

    CategoryDTO updateACategory(int id, CategoryDTO categoryDTO);

    CategoryDTO addACategory(CategoryDTO categoryDTO);

    CategoryDTO deleteACategory(int id);

    Set<CategoryDTO> findByName(String name);
    Page<CategoryDTO> findByName(String name, int pageNumber);
}
