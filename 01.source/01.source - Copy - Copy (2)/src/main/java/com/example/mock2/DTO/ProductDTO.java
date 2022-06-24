package com.example.mock2.DTO;

import com.example.mock2.Entity.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {

    private long productId;
    @NotBlank(message = "product name must not blank")
    private String productName;
    @Min(value = 1,message = "product price must higher than 0")
    private long productPrice;
    @Min(value = 0,message = "product Quantity must not smaller than 0")
    private int productQuantity;
    private Float productRating;
    private Category category;
    @Min(value = 1,message = "categoryId must higher than 0")
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
