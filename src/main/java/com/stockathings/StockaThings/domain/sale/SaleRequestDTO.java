package com.stockathings.StockaThings.domain.sale;

import java.math.BigDecimal;
import java.util.Date;

public record SaleRequestDTO(Date dataVenda, BigDecimal totalVenda, Long tipoPagamento) {
}
