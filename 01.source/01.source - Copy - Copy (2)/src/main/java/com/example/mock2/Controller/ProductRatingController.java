package com.example.mock2.Controller;

import com.example.mock2.DTO.ProductRatingDTO;
import com.example.mock2.Service.LogService;
import com.example.mock2.Service.ProductRatingService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;

@AllArgsConstructor
@RestController
public class ProductRatingController {

    private ProductRatingService ProductRatingService;
    private LogService logService;
    public static final Logger logger = LoggerFactory.getLogger(ProductRatingController.class);

//    //tra ve toan bo cac the loai
//    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
//    @GetMapping("/ProductRating")
//    public ResponseEntity<Set<ProductRatingDTO>> getAllProductRating(){
//        return ResponseEntity.status(HttpStatus.OK).body(ProductRatingService.findAllProductRating());
//    }


//    //Tra ve danh sach cac the loai phan theo trang
//    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
//    @GetMapping("/ProductRating/{pageNumber}")
//    public ResponseEntity<Page<ProductRatingDTO>> getAllProductRatingPagination(@PathVariable int pageNumber){
//        return ResponseEntity.status(HttpStatus.OK).body(ProductRatingService.findAllProductRating(pageNumber));
//    }

//    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
//    @GetMapping("/ProductRating")
//    public ResponseEntity<Set<ProductRatingDTO>> getProductRatingByProductId(@RequestParam String productName){
//        return ResponseEntity.status(HttpStatus.OK).body(ProductRatingService.findByName(name));
//    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/ProductRating/{productId}")
    public ResponseEntity<List<ProductRatingDTO>> getAProductRatingByProduct(@PathVariable @Min(value = 1,message = "product id must higher than 0") long productId){

        logService.info(USERNAME,"get a product rating list with product id:"+productId);
        logger.info(USERNAME+" get a product rating list with product id:"+productId);

        return ResponseEntity.status(HttpStatus.OK).body(ProductRatingService.getProductRatingByProduct(productId));
    }
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/ProductRating/{productId}/{userId}")
    public ResponseEntity<ProductRatingDTO> getAProductRatingByUser(@PathVariable @Min(value = 1,message = "product id must higher than 0") long productId,@PathVariable @Min(value = 1,message = "user id must higher than 0") long userId){
        logService.info(USERNAME,"get a product rating with product id:"+productId+" and userId: "+userId);
        logger.info(USERNAME+" get a product rating with product id:"+productId+" and userId: "+userId);
        return ResponseEntity.status(HttpStatus.OK).body(ProductRatingService.getAProductRatingByUser(userId,productId));
    }

    @Secured({ "ROLE_USER" })
    @PutMapping("/ProductRating/{id}")
    public ResponseEntity<ProductRatingDTO> updateAProductRating(@PathVariable @Min(value = 1,message = "product rating id must higher than 0") int id,@Valid ProductRatingDTO ProductRatingDTO){
        logService.info(USERNAME,"update product rating by id: "+id+" with info:"+ProductRatingDTO);
        logger.info(USERNAME+" update product rating by id: "+id+" with info:"+ProductRatingDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ProductRatingService.updateAProductRating(id,ProductRatingDTO));
    }


    @Secured({ "ROLE_USER" })
    @PostMapping("/ProductRating")
    public ResponseEntity<ProductRatingDTO> addAProductRating(@Valid ProductRatingDTO ProductRatingDTO){
        logService.info(USERNAME,"add a product rating with info:"+ProductRatingDTO);
        logger.info(USERNAME+" add a product rating with info:"+ProductRatingDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ProductRatingService.addAProductRating(ProductRatingDTO));
    }


}
