package com.stockathings.StockaThings.repositories;

import com.stockathings.StockaThings.domain.product.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"unidadeMedida", "categoria"})
    Page<Product> findAll(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.idProduto = :id")
    Optional<Product> findByIdForUpdate(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Product p where p.idProduto = :id and p.usuario.id = :usuarioId")
    int deleteByIdAndUsuarioId(@Param("id") Long id, @Param("usuarioId") UUID usuarioId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
              update Product p
                 set p.qtdProduto = p.qtdProduto + :qtd
               where p.idProduto = :productId
                 and p.usuario.id = :usuarioId
            """)
    int addStock(@Param("productId") Long productId,
                 @Param("usuarioId") java.util.UUID usuarioId,
                 @Param("qtd") int qtd);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
               select p from Product p
               where p.idProduto = :id and p.usuario.id = :usuarioId
            """)
    Optional<Product> findByIdAndUsuarioIdForUpdate(@Param("id") Long id,
                                                    @Param("usuarioId") UUID usuarioId);
}

