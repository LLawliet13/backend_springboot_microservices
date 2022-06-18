package com.example.mock2.Service;

import com.example.mock2.DTO.ProductRatingDTO;
import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.Rating;
import com.example.mock2.Repository.ProductRatingRepository;
import com.example.mock2.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductRatingServiceImpl implements ProductRatingService{
    private static final int NUMBER_OF_ENTITY_PER_PAGE = 10;

    private ProductRatingRepository productRatingRepository;
    private ProductRepository productRepository;
    @Override
    public ProductRatingDTO updateAProductRating(long id, ProductRatingDTO productRatingDTO) {
        Rating productRating = productRatingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No Rating id found")
        );
        productRating.setVote(productRatingDTO.getVote());
        productRatingRepository.save(productRating);
        updateProductRating(productRatingDTO);
        return productRatingDTO;
    }

    @Override
    public ProductRatingDTO addAProductRating(ProductRatingDTO productRatingDTO) {
        Rating productRating = productRatingRepository.save(productRatingDTO.convertToProductRating());
        updateProductRating(productRatingDTO);
        return productRating.convertToProductRatingDTO();
    }

    @Override
    public ProductRatingDTO getAProductRatingByUser(long userid, long productId) {
        return productRatingRepository.findByUserIdAndProductId(userid,productId).orElseThrow(
                () -> new RuntimeException("No UserId And ProductId Matched")
        ).convertToProductRatingDTO();
    }

    @Override
    public List<ProductRatingDTO> getProductRatingByProduct(long productId) {
        return productRatingRepository.findByProductId(productId).stream().map(
                p -> p.convertToProductRatingDTO()
        ).collect(Collectors.toList());
    }

    private void updateProductRating(ProductRatingDTO productRatingDTO){
        Product product = productRepository.findById(productRatingDTO.getProductId()).orElseThrow(
                () -> new RuntimeException("No Product Id Found")
        );
        Float rating;
        Set<Rating> ratings = product.getRatings();
        if (ratings == null) rating = null;
        else {
            rating = 0f;

            for (Iterator<Rating> it = ratings.iterator(); it.hasNext(); ) {
                Rating r = it.next();
                rating += r.getVote();
            }
            rating /= ratings.size();
        }
        product.setProductRating(rating);
        productRepository.save(product);
    }

}
