package com.example.mock2.DTO;

import com.example.mock2.Entity.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private long productId;
    private String productName;
    private long productPrice;
    private int productQuantity;
    private float productRating;
    private Category category;
    private int categoryId;
    private Float rating;
    private Set<ProductMedia> productMediaSet;


    public ProductDTO(long productId, String productName, long productPrice, int productQuantity, float productRating, Category category, Float rating, Set<ProductMedia> productMediaSet) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productRating = productRating;
        this.category = category;
        this.rating = rating;
        this.productMediaSet = productMediaSet;
        this.categoryId = category.getCategoryId();
    }

    public Product convertToProduct(){
        return new Product(productId, productName,productPrice,
                productQuantity, productRating, categoryId
                );
    }
}
