package com.example.mock2.Service;

import com.example.mock2.DTO.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ProductService {
    Page<ProductDTO> findAllProduct(int pageNumber);
    List<ProductDTO> findAllProduct();


    ProductDTO updateAProduct(long id, ProductDTO ProductDTO, MultipartFile[] multipartFile);

    ProductDTO addAProduct(ProductDTO ProductDTO, MultipartFile[] multipartFile);

    ProductDTO deleteAProduct(long id);

    List<ProductDTO> findByName(String name);

    Page<ProductDTO> findByName(String name, int pageNumber);

    String uploadProductMedia(ProductDTO ProductDTO, MultipartFile[] multipartFile) throws IOException;

    Page<List<ProductDTO>> findByCategoryName(String name, int pageNumber);
    List<List<ProductDTO>> findByCategoryName(String name);

    boolean updateProductMedia(ProductDTO productDTO, MultipartFile[] multipartFiles);
}
