package com.stockathings.StockaThings.controllers;


import com.stockathings.StockaThings.domain.payment.Payment;
import com.stockathings.StockaThings.domain.payment.PaymentRequestDTO;
import com.stockathings.StockaThings.domain.payment.PaymentResponseDTO;
import com.stockathings.StockaThings.domain.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodopagamento")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> createPaymentMethod(@RequestBody PaymentRequestDTO body){
        Payment newPayment = this.paymentService.createPaymentMethod(body);
        return ResponseEntity.ok(newPayment);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPaymentMethodes(){
        List<PaymentResponseDTO> allPaymentsMethodes = paymentService.getAllPaymentMethodes();
        return ResponseEntity.ok(allPaymentsMethodes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentMethod(@PathVariable("id") Long idTipoPagamento) {
        paymentService.deletePaymentMethod(idTipoPagamento);
        return ResponseEntity.ok("Método de pagamento deletado com sucesso !");
    }
}

