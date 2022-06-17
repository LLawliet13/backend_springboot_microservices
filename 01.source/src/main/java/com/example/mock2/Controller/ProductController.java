package com.example.mock2.Controller;

import com.example.mock2.DTO.ProductDTO;
import com.example.mock2.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    //tra ve toan bo cac the loai
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/all")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProduct());
    }


    //Tra ve danh sach cac the loai phan theo trang
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/{pageNumber}")
    public ResponseEntity<Page<ProductDTO>> getAllProductPagination(@PathVariable int pageNumber){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProduct(pageNumber));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/Search/{name}")
    public ResponseEntity<List<ProductDTO>> getProductsByName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByName(name));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/Search/{name}/{pageNumber}")
    public ResponseEntity<Page<ProductDTO>> getProductsByNamePagination(@PathVariable String name,@PathVariable int pageNumber){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByName(name,pageNumber));
    }


    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/Search/Category/{name}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByCategoryName(name));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/Search/Category/{name}/{pageNumber}")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategoryNamePagination(@PathVariable String name,@PathVariable int pageNumber){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByCategoryNamePagination(name,pageNumber));
    }


    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/Product/{id}")
    public ResponseEntity<ProductDTO> updateAProduct(@PathVariable long id,ProductDTO ProductDTO,@RequestParam(name = "files",required = false)MultipartFile[] multipartFile){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateAProduct(id,ProductDTO,multipartFile));
    }


    @Secured({ "ROLE_ADMIN" })
    @PostMapping("/Product")
    public ResponseEntity<ProductDTO> addAProduct(ProductDTO ProductDTO,@RequestParam(name = "files",required = false)MultipartFile[] multipartFile){
        return ResponseEntity.status(HttpStatus.OK).body(productService.addAProduct(ProductDTO,multipartFile));

    }


    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/Product/{id}")
    public ResponseEntity<ProductDTO> deleteAProduct(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteAProduct(id));
    }
}
