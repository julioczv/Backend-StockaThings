package com.stockathings.StockaThings.domain.products;

import java.util.List;


public record PageableDTO<T> (
    int totalPages,
    int pageSize,
    int currentPage,
    long totalElements,
    List<T> content
) {}

