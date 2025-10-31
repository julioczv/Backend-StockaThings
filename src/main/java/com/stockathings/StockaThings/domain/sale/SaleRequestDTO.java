package com.stockathings.StockaThings.domain.sale;

import com.stockathings.StockaThings.domain.saleitem.SaleItemRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record SaleRequestDTO(Long tipoPagamentoId, List<SaleItemRequestDTO> items) {
}
