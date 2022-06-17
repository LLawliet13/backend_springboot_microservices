package com.example.mock2.Service;

import com.example.mock2.DTO.ProductRatingDTO;

import java.util.List;

public interface ProductRatingService {
    ProductRatingDTO updateAProductRating(long id, ProductRatingDTO productRatingDTO);

    ProductRatingDTO addAProductRating(ProductRatingDTO productRatingDTO);

    ProductRatingDTO getAProductRatingByUser(long userid,long productId);

    List<ProductRatingDTO> getProductRatingByProduct(long productId);
}
