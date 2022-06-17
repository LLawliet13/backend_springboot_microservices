package com.example.mock2.Service;

import com.example.mock2.DTO.ProductRatingDTO;
import org.springframework.stereotype.Service;

@Service
public class ProductRatingServiceImpl implements ProductRatingService{
    private static final int NUMBER_OF_ENTITY_PER_PAGE = 10;

    @Override
    public ProductRatingDTO updateAProductRating(int id, ProductRatingDTO productRatingDTO) {
        return null;
    }

    @Override
    public ProductRatingDTO addAProductRating(ProductRatingDTO productRatingDTO) {
        return null;
    }
}
