package com.stockathings.StockaThings.domain.sale;

import java.util.List;

public record SalePeriodDTO(
        SalePeriodSummaryDTO summary,
        List<SalesRangeResponseDTO> sales
) {}
