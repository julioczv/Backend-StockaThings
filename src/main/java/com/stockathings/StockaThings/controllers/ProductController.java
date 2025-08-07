package com.stockathings.StockaThings.controllers;


import com.stockathings.StockaThings.domain.products.PageableDTO;
import com.stockathings.StockaThings.domain.products.Product;
import com.stockathings.StockaThings.domain.products.ProductRequestDTO;
import com.stockathings.StockaThings.domain.products.ProductResponseDTO;
import com.stockathings.StockaThings.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductRequestDTO body){
        Product newProduct = this.productService.createProduct(body);
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping
    public ResponseEntity<PageableDTO<ProductResponseDTO>>
    getProduct(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int size){
            PageableDTO<ProductResponseDTO> allProducts = this.productService.getAllProducts(page, size);
            return ResponseEntity.ok(allProducts);
    }

/*    @PutMapping
    public ResponseEntity<Product> update(@RequestBody ProductRequestDTO body){}*/


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Produto deletado com sucesso !");
    }

}
