package com.stockathings.StockaThings.domain.sale;

import com.stockathings.StockaThings.domain.saleitem.SaleItemResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record SalesRangeResponseDTO(Long idVenda, LocalDateTime dataVenda, String pagamento,
                                    List<SaleItemResponseDTO> items,
                                    SalesRangeTotalDTO totals) {
}
