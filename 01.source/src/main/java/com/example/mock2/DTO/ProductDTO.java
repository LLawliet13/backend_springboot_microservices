package com.example.mock2.DTO;

import com.example.mock2.Entity.BillDetail;
import com.example.mock2.Entity.Cart;
import com.example.mock2.Entity.ProductMedia;
import com.example.mock2.Entity.Rating;
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
    private String categoryName;
    private float rating;
    private Set<ProductMedia> productMediaSet;
}
