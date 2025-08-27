package com.stockathings.StockaThings.controllers;


import com.stockathings.StockaThings.domain.category.Category;
import com.stockathings.StockaThings.domain.category.CategoryRequestDTO;
import com.stockathings.StockaThings.domain.category.CategoryResponseDTO;
import com.stockathings.StockaThings.domain.service.CategoryService;
import com.stockathings.StockaThings.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDTO body){
        Category category = this.categoryService.createCategory(body);
        return ResponseEntity.ok().body(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<CategoryResponseDTO> allCategories = this.categoryService.getAllCategories();
        return ResponseEntity.ok().body(allCategories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long idCategoria) {
        categoryService.deleteCategory(idCategoria);
        return ResponseEntity.ok("Categoria deletada com sucesso !");
    }

}
