package com.stockathings.StockaThings.domain.service;

import com.stockathings.StockaThings.domain.products.Product;
import com.stockathings.StockaThings.domain.products.ProductRequestDTO;
import com.stockathings.StockaThings.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
