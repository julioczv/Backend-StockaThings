package com.stockathings.StockaThings.repositories;

import com.stockathings.StockaThings.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNomeCategoria(String nomeCategoria);
}
