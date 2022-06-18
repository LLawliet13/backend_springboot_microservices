package com.example.mock2.Repository;

import com.example.mock2.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query ("SELECT p.productId FROM Product p WHERE p.productName = ?1")
    long getProductIdByProductName(String productName);

    @Query ("SELECT p.productName FROM Product p WHERE p.productId = ?1")
    String getProductNameByProductId(long productId);

    @Query ("SELECT p.productPrice FROM Product p WHERE p.productId = ?1")
    long getProductPrice(long productId);

    Product getProductByProductId(long productId);
}

