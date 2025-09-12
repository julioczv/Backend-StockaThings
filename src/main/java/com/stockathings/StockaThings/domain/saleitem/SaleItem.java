package com.stockathings.StockaThings.domain.saleitem;

import com.stockathings.StockaThings.domain.product.Product;
import com.stockathings.StockaThings.domain.sale.Sale;
import com.stockathings.StockaThings.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name="itensvenda")
@Data
public class SaleItem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="itv_id") private Long idItemVenda;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="venda_id", nullable=false)
    private Sale venda;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="produto_id", nullable=false)
    private Product produto;

    @Column(name="itv_qtde", nullable=false)
    private Integer quantidade;

    @Column(name="itv_valor", precision=12, scale=2, nullable=false)
    private BigDecimal precoUnitario;

    @Column(name="itv_custo")
    private BigDecimal precoCustoUnitario;
}
