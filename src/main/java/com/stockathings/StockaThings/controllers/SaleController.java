package com.stockathings.StockaThings.controllers;

import com.stockathings.StockaThings.domain.sale.Sale;
import com.stockathings.StockaThings.domain.sale.SaleRequestDTO;
import com.stockathings.StockaThings.domain.sale.SaleResponseDTO;
import com.stockathings.StockaThings.domain.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vendas")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponseDTO> create(@RequestBody SaleRequestDTO dto) {
        var resp = saleService.createSale(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> findAll() {
        return ResponseEntity.ok(saleService.findAllSales());
    }
}
