package com.store.dto.products.category;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
    //TODO check if mentioned category exists
    /*@Pattern(regexp = "^[A-Z][a-z]{1,49}$",
            message = "Parent category name must start with a capital letter and contain only alphabets (2-50 characters).")*/

    //@NotBlank(message="Category name is required.")
    /*@Pattern(regexp = "^[A-Z][a-z]{1,49}$",
            message = "Category name must start with a capital letter and contain only alphabets (2-50 characters).")*/
    private String categoryName;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
