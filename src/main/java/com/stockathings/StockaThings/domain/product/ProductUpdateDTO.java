package com.stockathings.StockaThings.domain.product;

import java.math.BigDecimal;

public record ProductUpdateDTO(String nomeProduto,
                               String descricaoProduto,
                               BigDecimal valorPagoProduto,
                               BigDecimal valorVendaProduto,
                               Integer qtdProduto,
                               Long unidadeMedidaId,
                               Long categoriaId) {
}
