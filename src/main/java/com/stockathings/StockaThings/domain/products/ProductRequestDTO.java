package com.stockathings.StockaThings.domain.products;

import java.math.BigDecimal;

public record ProductRequestDTO(String nomeProduto,
                                String descricaoProduto,
                                BigDecimal valorPagoProduto,
                                BigDecimal valorVendaProduto,
                                Integer quantidadeProduto,
                                Long unidadeMedidaId,
                                Long categoriaId

)
{

}
