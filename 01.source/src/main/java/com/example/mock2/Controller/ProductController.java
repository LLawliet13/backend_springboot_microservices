package com.example.mock2.Controller;

import com.example.mock2.DTO.ProductDTO;
import com.example.mock2.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    //tra ve toan bo cac the loai
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product")
    public ResponseEntity<Set<ProductDTO>> getAllProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProduct());
    }


    //Tra ve danh sach cac the loai phan theo trang
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/{pageNumber}")
    public ResponseEntity<Page<ProductDTO>> getAllProductPagination(@PathVariable int pageNumber){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProduct(pageNumber));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product")
    public ResponseEntity<Set<ProductDTO>> getProductsByName(@RequestParam String name){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByName(name));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Product/{pageNumber}")
    public ResponseEntity<Page<ProductDTO>> getProductsByNamePagination(@RequestParam String name,@RequestParam int pageNumber){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByName(name,pageNumber));
    }

    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/Product/{id}")
    public ResponseEntity<ProductDTO> updateAProduct(@PathVariable int id,ProductDTO ProductDTO){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateAProduct(id,ProductDTO));
    }


    @Secured({ "ROLE_ADMIN" })
    @PostMapping("/Product")
    public ResponseEntity<ProductDTO> addAProduct(ProductDTO ProductDTO){
        return ResponseEntity.status(HttpStatus.OK).body(productService.addAProduct(ProductDTO));
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/Product/{id}")
    public ResponseEntity<ProductDTO> deleteAProduct(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteAProduct(id));
    }
}
