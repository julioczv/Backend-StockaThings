package com.stockathings.StockaThings.domain.products;

import java.math.BigDecimal;
import java.util.UUID;

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
