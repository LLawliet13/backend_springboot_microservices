package com.example.mock2.Service;

import com.example.mock2.DTO.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ProductService {
    Page<ProductDTO> findAllProduct(int pageNumber);
    Set<ProductDTO> findAllProduct();

    ProductDTO updateAProduct(int id, ProductDTO ProductDTO);

    ProductDTO addAProduct(ProductDTO ProductDTO);

    ProductDTO deleteAProduct(int id);

    Set<ProductDTO> findByName(String name);

    Page<ProductDTO> findByName(String name, int pageNumber);
}
