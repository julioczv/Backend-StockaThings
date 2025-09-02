package com.stockathings.StockaThings.repositories;

import com.stockathings.StockaThings.domain.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("select s from Sale s join fetch s.tipoPagamento")
    List<Sale> findAllFetchPayment();
}
