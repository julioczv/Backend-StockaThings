package com.stockathings.StockaThings.domain.products;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(Long id, String nameProduct,
                                 String description,
                                 BigDecimal costPrice,
                                 BigDecimal sellingPrice,
                                 Integer stockQuantity,
                                 String type) {
}
