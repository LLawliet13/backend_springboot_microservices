package com.example.mock2.Controller;

import com.example.mock2.DTO.ProductRatingDTO;
import com.example.mock2.Service.ProductRatingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
public class ProductRatingController {

    private ProductRatingService ProductRatingService;

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
    public ResponseEntity<List<ProductRatingDTO>> getAProductRatingByProduct(@PathVariable long productId){
        return ResponseEntity.status(HttpStatus.OK).body(ProductRatingService.getProductRatingByProduct(productId));
    }
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/ProductRating/{productId}/{userId}")
    public ResponseEntity<ProductRatingDTO> getAProductRatingByUser(@PathVariable long productId,@PathVariable long userId){
        return ResponseEntity.status(HttpStatus.OK).body(ProductRatingService.getAProductRatingByUser(userId,productId));
    }

    @Secured({ "ROLE_USER" })
    @PutMapping("/ProductRating/{id}")
    public ResponseEntity<ProductRatingDTO> updateAProductRating(@PathVariable int id,ProductRatingDTO ProductRatingDTO){
        return ResponseEntity.status(HttpStatus.OK).body(ProductRatingService.updateAProductRating(id,ProductRatingDTO));
    }


    @Secured({ "ROLE_USER" })
    @PostMapping("/ProductRating")
    public ResponseEntity<ProductRatingDTO> addAProductRating(ProductRatingDTO ProductRatingDTO){
        return ResponseEntity.status(HttpStatus.OK).body(ProductRatingService.addAProductRating(ProductRatingDTO));
    }


}
