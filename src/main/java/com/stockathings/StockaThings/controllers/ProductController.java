package com.stockathings.StockaThings.controllers;


import com.stockathings.StockaThings.domain.product.PageableDTO;
import com.stockathings.StockaThings.domain.product.Product;
import com.stockathings.StockaThings.domain.product.ProductRequestDTO;
import com.stockathings.StockaThings.domain.product.ProductResponseDTO;
import com.stockathings.StockaThings.domain.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
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

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid ProductRequestDTO body
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, body));
    }


    @DeleteMapping("/{idProduto}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long idProduto) {
        productService.deleteProduct(idProduto);
        return ResponseEntity.ok("Produto deletado com sucesso !");
    }
}
