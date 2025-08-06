package com.stockathings.StockaThings.repositories;

import com.stockathings.StockaThings.domain.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>{

}
