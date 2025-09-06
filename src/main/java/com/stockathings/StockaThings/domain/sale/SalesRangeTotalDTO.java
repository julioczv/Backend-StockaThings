package com.stockathings.StockaThings.domain.sale;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalesRangeTotalDTO(LocalDate from, LocalDate to, int totalItens, BigDecimal custo, BigDecimal faturado, BigDecimal lucro) {
}
