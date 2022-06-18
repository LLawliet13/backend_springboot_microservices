package com.example.mock2.Service;

import com.example.mock2.DTO.ProductRatingDTO;
import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.Rating;
import com.example.mock2.Entity.User;
import com.example.mock2.Repository.ProductRatingRepository;
import com.example.mock2.Repository.ProductRepository;
import com.example.mock2.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductRatingServiceImpl implements ProductRatingService {
    private static final int NUMBER_OF_ENTITY_PER_PAGE = 10;

    private ProductRatingRepository productRatingRepository;
    private ProductRepository productRepository;

    private UserRepository userRepository;

    @Override
    public ProductRatingDTO updateAProductRating(long id, ProductRatingDTO productRatingDTO) {
        Rating productRating = productRatingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No Rating id found")
        );
        productRating.setVote(productRatingDTO.getVote());
        productRatingRepository.saveAndFlush(productRating);
        updateProductRating(productRatingDTO);
        return productRatingDTO;
    }

    static int i = 0;
    @Override
    @Transactional
    public ProductRatingDTO addAProductRating(ProductRatingDTO productRatingDTO) {
        Optional<Rating> ratingOptional = productRatingRepository.findByUserIdAndProductId(productRatingDTO.getUserId(),
                productRatingDTO.getProductId());
        if (ratingOptional.isPresent()) {
            throw new RuntimeException("this user voted this product");
        } else {
            User userOptional = userRepository.checkIfUserBoughtProduct(productRatingDTO.getUserId(),
                    productRatingDTO.getProductId());
            if (userOptional == null) {
                throw new RuntimeException("only user bought this product cant vote");
            } else {
                i++;
                System.out.println(i);
                productRatingRepository.saveAndFlush(productRatingDTO.convertToProductRating());
                updateProductRating(productRatingDTO);
                return productRatingDTO;
            }
        }
    }

    @Override
    public ProductRatingDTO getAProductRatingByUser(long userid, long productId) {
        return productRatingRepository.findByUserIdAndProductId(userid, productId).orElseThrow(
                () -> new RuntimeException("No UserId And ProductId Matched")
        ).convertToProductRatingDTO();
    }

    @Override
    public List<ProductRatingDTO> getProductRatingByProduct(long productId) {
        return productRatingRepository.findByProductId(productId).stream().map(
                p -> p.convertToProductRatingDTO()
        ).collect(Collectors.toList());
    }

    private void updateProductRating(ProductRatingDTO productRatingDTO) {
        Product product = productRepository.findById(productRatingDTO.getProductId()).orElseThrow(
                () -> new RuntimeException("No Product Id Found")
        );
        Float rating;
        Set<Rating> ratings = product.getRatings();//goi get k thong qua responseBody thi k bi loi call method twice ?
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
        productRepository.saveAndFlush(product);
    }

}
