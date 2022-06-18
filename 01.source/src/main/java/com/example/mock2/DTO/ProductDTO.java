package com.example.mock2.DTO;

import com.example.mock2.Entity.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {

    private long productId;
    private String productName;
    private long productPrice;
    private int productQuantity;
    private Float productRating;
    private Category category;
    private long categoryId;
    private Set<ProductMedia> productMediaSet;
    private int soldNumber;

    public ProductDTO(long productId, String productName, long productPrice, int productQuantity, Float productRating, Category category, Set<ProductMedia> productMediaSet,int soldNumber) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productRating = productRating;
        this.category = category;
        this.productMediaSet = productMediaSet;
        this.categoryId = category.getCategoryId();
        this.soldNumber = soldNumber;
    }

    public Product convertToProduct(){
        return new Product(productId, productName,productPrice,
                productQuantity, productRating, categoryId
                );
    }
}
