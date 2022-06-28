package com.example.mock2.DTO;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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
    @Min(value = 1,message = "categoryId must higher than 0")
    private long categoryId;
    private int soldNumber;



}
