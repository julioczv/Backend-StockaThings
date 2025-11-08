package com.stockathings.StockaThings.domain.product;

import java.math.BigDecimal;

public record ProductRequestDTO(String nomeProduto,
                                String descricaoProduto,
                                BigDecimal valorPagoProduto,
                                BigDecimal valorVendaProduto,
                                Integer qtdProduto,
                                Long unidadeMedidaId,
                                Long categoriaId

)
{

}
