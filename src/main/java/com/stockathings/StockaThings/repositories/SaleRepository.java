package com.stockathings.StockaThings.repositories;

import com.stockathings.StockaThings.domain.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("select s from Sale s join fetch s.tipoPagamento")
    List<Sale> findAllFetchPayment();

    @Query("""
           select s
             from Sale s
             join fetch s.tipoPagamento
            where s.dataVenda >= :start and s.dataVenda < :end
           """)
    List<Sale> findAllByPeriodoFetchPayment(@Param("start") LocalDateTime start,
                                            @Param("end")   LocalDateTime end);

    @Query("""
           select coalesce(sum(s.totalVenda), 0)
             from Sale s
            where s.dataVenda >= :start and s.dataVenda < :end
           """)
    BigDecimal sumTotalVendaByPeriodo(@Param("start") LocalDateTime start,
                                      @Param("end")   LocalDateTime end);
}
