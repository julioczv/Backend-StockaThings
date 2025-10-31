package com.stockathings.StockaThings.domain.sale;

import com.stockathings.StockaThings.domain.saleitem.SaleItemResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public record SaleResponseDTO(Long idVenda, LocalDateTime dataVenda, String pagamento, List<SaleItemResponseDTO> items, SaleTotalDTO total) {
}
