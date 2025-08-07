package com.stockathings.StockaThings.domain.products;

import java.math.BigDecimal;

public record ProductRequestDTO(String nameProduct,
                                String description,
                                BigDecimal costPrice,
                                BigDecimal sellingPrice,
                                Integer stockQuantity,
                                String type)
{

}
