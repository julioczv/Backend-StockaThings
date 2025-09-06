package com.stockathings.StockaThings.domain.saleitem;

import java.math.BigDecimal;

public record SaleItemResponseDTO(Long idItemVenda, Long idProduto, String nomeProduto, Integer quantidade,
                                  BigDecimal precoUnitario, BigDecimal subTotalVenda, BigDecimal precoPago, BigDecimal subTotalPago
) {
}
