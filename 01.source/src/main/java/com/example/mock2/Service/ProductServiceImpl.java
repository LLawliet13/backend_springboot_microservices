package com.example.mock2.Service;

import com.example.mock2.DTO.ProductDTO;
import com.example.mock2.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    private ProductDTO convertToDTO(){
        return null;
    }
}
