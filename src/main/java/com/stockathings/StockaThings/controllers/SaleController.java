package com.stockathings.StockaThings.controllers;

import com.stockathings.StockaThings.domain.sale.*;
import com.stockathings.StockaThings.domain.service.SaleService;
import com.stockathings.StockaThings.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponseDTO> create(@RequestBody SaleRequestDTO in,
                                                  @AuthenticationPrincipal User me) {
        var resp = saleService.createSale(in, me);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    /*@GetMapping
    public ResponseEntity<List<SaleResponseDTO>> findAll() {
        return ResponseEntity.ok(saleService.findAllSales());
    }*/

    @GetMapping("/periodo")
    public SalePeriodDTO porPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return saleService.findByDateRange(from, to);
    }

    @DeleteMapping("/{idVenda}")
    public ResponseEntity<Void> deleteByIdVenda(@PathVariable Long idVenda, @AuthenticationPrincipal User me) {
        saleService.deleteSale(idVenda, me);
        return  ResponseEntity.ok().build();
    }
}
