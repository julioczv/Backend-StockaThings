package com.stockathings.StockaThings.domain.sale;

import java.math.BigDecimal;

public record SaleTotalDTO(int totalItens, BigDecimal totalVenda,  BigDecimal valorPagoProduto) {
}
