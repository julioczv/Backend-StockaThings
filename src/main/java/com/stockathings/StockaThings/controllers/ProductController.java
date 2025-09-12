package com.stockathings.StockaThings.controllers;


import com.stockathings.StockaThings.domain.product.PageableDTO;
import com.stockathings.StockaThings.domain.product.Product;
import com.stockathings.StockaThings.domain.product.ProductRequestDTO;
import com.stockathings.StockaThings.domain.product.ProductResponseDTO;
import com.stockathings.StockaThings.domain.service.ProductService;
import com.stockathings.StockaThings.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(produces="application/json", consumes="application/json")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO in,
                                                     @AuthenticationPrincipal User me) {
        var dto = productService.createProduct(in, me);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public ResponseEntity<PageableDTO<ProductResponseDTO>>
    getProduct(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int size){
            PageableDTO<ProductResponseDTO> allProducts = this.productService.getAllProducts(page, size);
            return ResponseEntity.ok(allProducts);
    }

/*    @PutMapping
    public ResponseEntity<Product> update(@RequestBody ProductRequestDTO body){}*/


    @DeleteMapping("/{idProduto}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long idProduto, @AuthenticationPrincipal User me) {
        productService.deleteProduct(idProduto, me);
        return ResponseEntity.ok("Produto deletado com sucesso !");
    }
}
