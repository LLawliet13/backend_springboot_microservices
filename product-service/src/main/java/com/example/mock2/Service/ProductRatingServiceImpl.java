package com.example.mock2.Service;

import com.example.mock2.DTO.ProductRatingDTO;
import com.example.mock2.DTO.UserDTO;
import com.example.mock2.Entity.Product;
import com.example.mock2.Entity.Rating;
import com.example.mock2.Repository.ProductRatingRepository;
import com.example.mock2.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;
import static com.example.mock2.filter.CustomAuthorizationFilter.token_in_header;

@Service
@AllArgsConstructor
public class ProductRatingServiceImpl implements ProductRatingService {
    private static final int NUMBER_OF_ENTITY_PER_PAGE = 10;

    private ProductRatingRepository productRatingRepository;
    private ProductRepository productRepository;

    private RestTemplate restTemplate;


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

        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",token_in_header);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        UserDTO userDTO = restTemplate.exchange("http://user-service/User/Search/"+USERNAME,
                HttpMethod.GET,entity,UserDTO.class).getBody();

        Optional<Rating> ratingOptional = productRatingRepository.findByUserIdAndProductId(userDTO.getUserId(),
                productRatingDTO.getProductId());
        if (ratingOptional.isPresent()) {
            throw new RuntimeException("this user voted this product");
        } else {
            UserDTO userOptional =
                    restTemplate.exchange("http://user-service/User/Check/"+USERNAME+"/"+productRatingDTO.getProductId(),HttpMethod.GET,entity,
                            UserDTO.class).getBody();
//                    userRepository.checkIfUserBoughtProduct(productRatingDTO.getUserId(),
//                    productRatingDTO.getProductId());
            if (userOptional == null) {
                throw new RuntimeException("only user bought this product cant vote");
            } else {


                productRatingRepository.saveAndFlush(productRatingDTO.convertToProductRating());
                updateProductRating(productRatingDTO);
                return productRatingDTO;
            }
        }
    }

    @Override
    public ProductRatingDTO getAProductRatingByUser(long userid, long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",token_in_header);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);





        Rating productRating =   productRatingRepository.findByUserIdAndProductId(userid, productId).orElseThrow(
                () -> new RuntimeException("No UserId And ProductId Matched")
        );
        UserDTO userDTO = restTemplate.exchange("http://user-service/User/"+productRating.getUserId(),
                HttpMethod.GET,entity,UserDTO.class).getBody();
        productRating.setUser(userDTO);
        return productRating.convertToProductRatingDTO();

    }

    @Override
    public List<ProductRatingDTO> getProductRatingByProduct(long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization",token_in_header);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);


        return productRatingRepository.findByProductId(productId).stream().map(
                p -> {
                    UserDTO userDTO = restTemplate.exchange("http://user-service/User/"+p.getUserId(),
                            HttpMethod.GET,entity,UserDTO.class).getBody();
                    p.setUser(userDTO);
                    return p.convertToProductRatingDTO();
                }
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
