package com.example.mock2.Controller;

import com.example.mock2.DTO.CategoryDTO;
import com.example.mock2.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class CategoryController {

    private CategoryService categoryService;

    //tra ve toan bo cac the loai
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Category")
    public ResponseEntity<List<CategoryDTO>> getAllCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllCategory());
    }


    //Tra ve danh sach cac the loai phan theo trang
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Category/{pageNumber}")
    public ResponseEntity<Page<CategoryDTO>> getAllCategoryPagination(@PathVariable @Min(value = 0,message = "pageNumber shouldn't be smaller than 0") int pageNumber){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllCategory(pageNumber));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Category/Search/{name}/{pageNumber}")
    public ResponseEntity<Page<CategoryDTO>> getCategoriesByNamePagination(@PathVariable String name, @PathVariable(required = true) int pageNumber){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findByName(name, pageNumber));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Category/Search/{name}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByName(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findByName(name));
    }



    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/Category/{id}")
    public ResponseEntity<CategoryDTO> updateACategory(@PathVariable int id,CategoryDTO categoryDTO){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateACategory(id,categoryDTO));
    }


    @Secured({ "ROLE_ADMIN" })
    @PostMapping("/Category")
    public ResponseEntity<CategoryDTO> addACategory(@Valid CategoryDTO categoryDTO){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.addACategory(categoryDTO));
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/Category/{id}")
    public ResponseEntity<CategoryDTO> deleteACategory(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteACategory(id));
    }





}
