package com.stockathings.StockaThings.controllers;


import com.stockathings.StockaThings.domain.products.Product;
import com.stockathings.StockaThings.domain.products.ProductRequestDTO;
import com.stockathings.StockaThings.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
