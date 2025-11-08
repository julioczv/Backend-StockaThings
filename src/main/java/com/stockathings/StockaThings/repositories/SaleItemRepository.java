package com.stockathings.StockaThings.repositories;

import com.stockathings.StockaThings.domain.saleitem.SaleItem;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
    @Query("""
           select si
             from SaleItem si
             join fetch si.produto p
            where si.venda.idVenda in :saleIds
           """)
    List<SaleItem> findByVendaIdInFetchProduct(@Param("saleIds") List<Long> saleIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
     select si from SaleItem si
     join fetch si.produto p
     where si.venda.idVenda = :saleId
  """)
    java.util.List<SaleItem> findByVendaIdFetchProductForUpdate(@Param("saleId") Long saleId);
}
