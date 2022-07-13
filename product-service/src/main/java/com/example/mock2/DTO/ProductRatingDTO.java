package com.example.mock2.DTO;

import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.Rating;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class ProductRatingDTO {
    @Min(value = 1,message = "vote must higher than 0")
    @Max(value = 5,message = "vote must smaller than 6")
    private int vote;

    @Min(value = 1,message = "product id must higher than 0")
    private long productId;


    private String productName;

    private String userName;

    @Min(value = 1,message = "user id must higher than 0")
    private long userId;

    public ProductRatingDTO(long productId, long userId, String productName, String userFullName, int vote) {
        this.productId = productId;
        this.userId = userId;
        this.productName = productName;
        this.userName = userFullName;
        this.vote = vote;
    }

    public Rating convertToProductRating() {
        return new Rating(productId,userId,vote);
    }
}
