package com.example.mock2.Service;

import com.example.mock2.DTO.CategoryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Page<CategoryDTO> findAllCategory(int pageNumber);
    List<CategoryDTO> findAllCategory();

    CategoryDTO updateACategory(long id, CategoryDTO categoryDTO);

    CategoryDTO addACategory(CategoryDTO categoryDTO);

    CategoryDTO deleteACategory(long id);

    List<CategoryDTO> findByName(String name);
    Page<CategoryDTO> findByName(String name, int pageNumber);
}
