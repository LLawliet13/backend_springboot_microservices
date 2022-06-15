package com.example.mock2.Service;

import com.example.mock2.DTO.ProductRatingDTO;

public interface ProductRatingService {
    ProductRatingDTO updateAProductRating(int id, ProductRatingDTO productRatingDTO);

    ProductRatingDTO addAProductRating(ProductRatingDTO productRatingDTO);

}
