package com.stockathings.StockaThings.repositories;

import com.stockathings.StockaThings.domain.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long>{

    @EntityGraph(attributePaths = {"unidadeMedida", "categoria"})
    Page<Product> findAll(Pageable pageable);

}
