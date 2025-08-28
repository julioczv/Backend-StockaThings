package com.stockathings.StockaThings.repositories;

import com.stockathings.StockaThings.domain.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale,Long> {

}
