package com.example.mock2.Service;

import com.example.mock2.Entity.Product;
import com.example.mock2.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public long getProductIdByProductName(String productName) {
        return productRepository.getProductIdByProductName(productName);
    }

    public Product getProductByProductId(long productId) {
        return productRepository.getProductByProductId(productId);
    }
}
