package com.example.mock2.DTO;

import com.example.mock2.Entity.Category;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDTO {
    private long categoryId;

    @NotBlank(message = "category name must not blank")
    private String categoryName;
    public Category convertToCategory(){
        return new Category(categoryId,categoryName);
    }
}
