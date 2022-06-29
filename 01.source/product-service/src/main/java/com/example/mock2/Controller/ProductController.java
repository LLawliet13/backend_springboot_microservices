package com.example.mock2.Controller;

import com.example.mock2.DTO.ProductDTO;
import com.example.mock2.Service.LogService;
import com.example.mock2.Service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;

@RestController
@AllArgsConstructor
@Validated
public class ProductController {
    private ProductService productService;
    private LogService logService;
    public static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    //tra ve toan bo cac the loai
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/all")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        logService.info(USERNAME,"view All Product");
        logger.info(USERNAME+" view All Product");
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProduct());
    }


    //Tra ve danh sach cac the loai phan theo trang
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/{pageNumber}")
    public ResponseEntity<Page<ProductDTO>> getAllProductPagination(@PathVariable  @Min(value = 0,message = "pageNumber shouldn't be smaller than 0") int pageNumber){
        logService.info(USERNAME,"view All Product Pagination: "+pageNumber);
        logger.info(USERNAME+" view All Product Pagination: "+pageNumber);

        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProduct(pageNumber));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/SearchId/{productId}")
    public ResponseEntity<ProductDTO> getProductsById(@PathVariable long productId){
        logService.info(USERNAME,"view All Product by Id: "+productId);
        logger.info(USERNAME+" view All Product by Id: "+productId);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductByProductId(productId));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/Search/{name}")
    public ResponseEntity<List<ProductDTO>> getProductsByName(@PathVariable  @NotBlank(message = "product name shouldn't be blank ") String name){
        logService.info(USERNAME,"view All Product by name: "+name);
        logger.info(USERNAME+" view All Product by name: "+name);
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByName(name));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/SearchExact/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findExactByName(name));
    }


    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/Search/{name}/{pageNumber}")
    public ResponseEntity<Page<ProductDTO>> getProductsByNamePagination(@PathVariable @NotBlank(message = "product name shouldn't be blank ") String name,
                                                                        @PathVariable  @Min(value = 0,message = "pageNumber shouldn't be smaller than 0") int pageNumber){
        logService.info(USERNAME,"view All Product by name: "+name+"with Pagination:"+pageNumber);
        logger.info(USERNAME+" view All Product by name: "+name+"with Pagination:"+pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByName(name,pageNumber));
    }


    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/Search/Category/{name}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryName(@PathVariable @NotBlank(message = "category name shouldn't be blank ") String name){
        logService.info(USERNAME,"view All Product by Category name: "+name);
        logger.info(USERNAME+" view All Product  by Category name: "+name);
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByCategoryName(name));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/Search/Category/{name}/{pageNumber}")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategoryNamePagination(@PathVariable @NotBlank(message = "category name shouldn't be blank ") String name,@PathVariable  @Min(value = 0,message = "pageNumber shouldn't be smaller than 0") int pageNumber){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByCategoryNamePagination(name,pageNumber));
    }


    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/Product/{id}")
    public ResponseEntity<ProductDTO> updateAProduct(@PathVariable @Min(value = 1,message = "id shouldn't be smaller than 1") long id, @Valid ProductDTO ProductDTO, @RequestParam(name = "files",required = false)MultipartFile[] multipartFile){
        logService.info(USERNAME,"update product by id: "+id+" with info:"+ProductDTO);
        logger.info(USERNAME+" update product by id: "+id+" with info:"+ProductDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateAProduct(id,ProductDTO,multipartFile));
    }


    @Secured({ "ROLE_ADMIN" })
    @PostMapping("/Product")
    public ResponseEntity<ProductDTO> addAProduct(@Valid ProductDTO ProductDTO,@RequestParam(name = "files",required = false)MultipartFile[] multipartFile){
        logService.info(USERNAME,"add a product with info:"+ProductDTO);
        logger.info(USERNAME+" add a product with info:"+ProductDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productService.addAProduct(ProductDTO,multipartFile));

    }


    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/Product/{id}")
    public ResponseEntity<ProductDTO> deleteAProduct(@PathVariable @Min(value = 1,message = "id shouldn't be smaller than 1") long id){
        logService.info(USERNAME,"add a Product with id:"+id);
        logger.info(USERNAME+" add a Product with id:"+id);
        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteAProduct(id));
    }
}
