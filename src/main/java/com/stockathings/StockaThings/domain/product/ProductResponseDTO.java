package com.stockathings.StockaThings.domain.product;

import java.math.BigDecimal;

public record ProductResponseDTO(Long idProduto,
                                 String nomeProduto,
                                 String descricaoProduto,
                                 BigDecimal valorPagoProduto,
                                 BigDecimal valorVendaProduto,
                                 Integer QuantidadeProduto,
                                 Long idUnidadeMedida,
                                 String nomeUnidadeMedida,
                                 Long idCategoria,
                                 String nomeCategoria) {
}
