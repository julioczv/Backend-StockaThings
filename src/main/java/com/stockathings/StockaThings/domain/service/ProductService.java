package com.stockathings.StockaThings.domain.service;

import com.stockathings.StockaThings.domain.products.PageableDTO;
import com.stockathings.StockaThings.domain.products.Product;
import com.stockathings.StockaThings.domain.products.ProductRequestDTO;
import com.stockathings.StockaThings.domain.products.ProductResponseDTO;
import com.stockathings.StockaThings.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service //Diz para a classe que ela é do tipo service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product createProduct(ProductRequestDTO data /*Aqui chamados a nossa DTO de data*/){ //Passamos a nossa DTO para mapeala
        Product product = new Product(); //Instanciamos a classe para a poder receber os campos

        product.setNameProduct(data.nameProduct());
        product.setDescription(data.description());
        product.setCostPrice(data.costPrice());
        product.setSellingPrice(data.sellingPrice());
        product.setStockQuantity(data.stockQuantity());
        product.setType(data.type());

        repository.save(product);

        return product;
    }

    public PageableDTO<ProductResponseDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = repository.findAll(pageable);

        List<ProductResponseDTO> dtoList = productsPage.map(product -> new ProductResponseDTO(
                product.getId(),
                product.getNameProduct(),
                product.getDescription(),
                product.getCostPrice(),
                product.getSellingPrice(),
                product.getStockQuantity(),
                product.getType()
        )).getContent();

        return new PageableDTO<>(
                productsPage.getTotalPages(),
                productsPage.getSize(),
                productsPage.getNumber(),
                productsPage.getTotalElements(),
                dtoList
        );
    }


    public String deleteProduct(UUID id){
        repository.deleteById(id);
        return "Produto deletado com sucesso !";
    }
}
