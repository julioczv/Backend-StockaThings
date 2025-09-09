package com.stockathings.StockaThings.domain.service;


import com.stockathings.StockaThings.domain.payment.Payment;
import com.stockathings.StockaThings.domain.payment.PaymentRequestDTO;
import com.stockathings.StockaThings.domain.payment.PaymentResponseDTO;
import com.stockathings.StockaThings.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment createPaymentMethod(PaymentRequestDTO data) {
        if (paymentRepository.existsByTipoPagamento(data.tipoPagamento())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Método de pagamento ja cadastrado"
            );
        } else {
            Payment payment = new Payment();

            payment.setTipoPagamento(data.tipoPagamento());

            payment = paymentRepository.save(payment);

            return payment;
        }
    }

    public List<PaymentResponseDTO> getAllPaymentMethodes() {
        return paymentRepository
                .findAll()
                .stream()
                .map(
                payment ->
                new PaymentResponseDTO(payment.getIdTipoPagamento(), payment.getTipoPagamento())
                ).toList();
    }

    public String deletePaymentMethod(Long idTipoPagamento) {
        paymentRepository.deleteById(idTipoPagamento);

        return "O método de pagamento foi deletado com sucesso!";
    }
}
