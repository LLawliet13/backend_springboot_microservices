package com.example.mock2.Controller;

import com.example.mock2.DTO.CategoryDTO;
import com.example.mock2.Service.CategoryService;
import com.example.mock2.Service.LogService;
import com.example.mock2.filter.CustomAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import static com.example.mock2.filter.CustomAuthorizationFilter.USERNAME;
@RestController
@AllArgsConstructor
@Validated
public class CategoryController {
    private CategoryService categoryService;

    private LogService logService;
    public static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    //tra ve toan bo cac the loai
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Category")
    public ResponseEntity<List<CategoryDTO>> getAllCategory(){
        logService.info(USERNAME,"view All Category");
        logger.info(USERNAME+" view All Category");
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllCategory());
    }


    //Tra ve danh sach cac the loai phan theo trang
    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Category/{pageNumber}")
    public ResponseEntity<Page<CategoryDTO>> getAllCategoryPagination(@PathVariable @Min(value = 0,message = "pageNumber shouldn't be smaller than 0") int pageNumber){
        logService.info(USERNAME,"view All Category Pagination: "+pageNumber);
        logger.info(USERNAME+" view All Category Pagination: "+pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllCategory(pageNumber));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Category/Search/{name}/{pageNumber}")
    public ResponseEntity<Page<CategoryDTO>> getCategoriesByNamePagination(@PathVariable @NotBlank(message = "category name required") String name,
                                                                           @PathVariable(required = true) @Min(value = 0,message = "pageNumber shouldn't be smaller than 0") int pageNumber){
        logService.info(USERNAME,"view All Category by name: "+name+"with Pagination:"+pageNumber);
        logger.info(USERNAME+" view All Category by name: "+name+"with Pagination:"+pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findByName(name, pageNumber));
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/Category/Search/{name}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByName(@PathVariable @NotBlank(message = "category name required") String name){
        logService.info(USERNAME,"view All Category by name: "+name);
        logger.info(USERNAME+" view All Category by name: "+name);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findByName(name));
    }



    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/Category/{id}")
    public ResponseEntity<CategoryDTO> updateACategory(@PathVariable  @Min(value = 1,message = "categoryID shouldn't be smaller than 1") int id,@Valid CategoryDTO categoryDTO){
        logService.info(USERNAME,"update Category by id: "+id+" with info:"+categoryDTO);
        logger.info(USERNAME+" update Category by id: "+id+" with info:"+categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateACategory(id,categoryDTO));
    }


    @Secured({ "ROLE_ADMIN" })
    @PostMapping("/Category")
    public ResponseEntity<CategoryDTO> addACategory(@Valid CategoryDTO categoryDTO){
        logService.info(USERNAME,"add a Category with info:"+categoryDTO);
        logger.info(USERNAME+" add a Category with info:"+categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.addACategory(categoryDTO));
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/Category/{id}")
    public ResponseEntity<CategoryDTO> deleteACategory(@PathVariable  @Min(value = 1,message = "categoryID shouldn't be smaller than 1") int id){
        logService.info(USERNAME,"add a Category with id:"+id);
        logger.info(USERNAME+" add a Category with id:"+id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteACategory(id));
    }





}
