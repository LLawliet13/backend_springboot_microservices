package com.example.mock2.DTO;

import com.example.mock2.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private long categoryId;

    @NotBlank(message = "name must not blank")
    private String categoryName;
    public Category convertToCategory(){
        return new Category(categoryId,categoryName);
    }
}
