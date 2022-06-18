package com.example.mock2.Service;

import com.example.mock2.DTO.ProductDTO;
import com.example.mock2.Entity.ProductMedia;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import com.example.mock2.Entity.Product;


public interface ProductService {
    Page<ProductDTO> findAllProduct(int pageNumber);
    List<ProductDTO> findAllProduct();


    ProductDTO updateAProduct(long id, ProductDTO ProductDTO, MultipartFile[] multipartFile);

    ProductDTO addAProduct(ProductDTO ProductDTO, MultipartFile[] multipartFile);

    ProductDTO deleteAProduct(long id);

    List<ProductDTO> findByName(String name);

    Page<ProductDTO> findByName(String name, int pageNumber);

    Set<ProductMedia> uploadProductMedia(ProductDTO ProductDTO, MultipartFile[] multipartFile) throws IOException;

    Page<ProductDTO> findByCategoryNamePagination(String name, int pageNumber);

    List<ProductDTO> findByCategoryName(String name);

    Set<ProductMedia> updateProductMedia(ProductDTO productDTO, MultipartFile[] multipartFiles);

    long getProductIdByProductName(String productName);

    Product getProductByProductId(long productId);

}
