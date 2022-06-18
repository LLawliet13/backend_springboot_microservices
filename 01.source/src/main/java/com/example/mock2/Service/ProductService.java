package com.example.mock2.Service;

import com.example.mock2.Entity.Product;

public interface ProductService {

    long getProductIdByProductName(String productName);

    Product getProductByProductId(long productId);
}
