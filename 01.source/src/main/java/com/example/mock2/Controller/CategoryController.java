package com.example.mock2.Controller;

import com.example.mock2.DTO.CategoryDTO;
import com.example.mock2.DTO.ProductDTO;
import com.example.mock2.Entity.Category;
import com.example.mock2.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    //tra ve toan bo cac the loai
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Category")
    public ResponseEntity<Set<CategoryDTO>> getAllCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllCategory());
    }


    //Tra ve danh sach cac the loai phan theo trang
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Category/{pageNumber}")
    public ResponseEntity<Page<CategoryDTO>> getAllCategoryPagination(@PathVariable int pageNumber){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllCategory(pageNumber));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Category/Search")
    public ResponseEntity<Set<CategoryDTO>> getCategoriesByName(@RequestParam String name,@RequestParam(required = false) int pageNumber){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findByName(name));
    }



    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/Category/{id}")
    public ResponseEntity<CategoryDTO> updateACategory(@PathVariable int id,CategoryDTO categoryDTO){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateACategory(id,categoryDTO));
    }


    @Secured({ "ROLE_ADMIN" })
    @PostMapping("/Category")
    public ResponseEntity<CategoryDTO> addACategory(CategoryDTO categoryDTO){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.addACategory(categoryDTO));
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/Category/{id}")
    public ResponseEntity<CategoryDTO> deleteACategory(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteACategory(id));
    }





}
