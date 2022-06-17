package com.example.mock2.DTO;

import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.Rating;
import com.example.mock2.Entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRatingDTO {

    private int vote;

    private long productId;

    private String productName;

    private String userName;

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
