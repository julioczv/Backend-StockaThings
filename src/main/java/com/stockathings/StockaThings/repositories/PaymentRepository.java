package com.stockathings.StockaThings.repositories;

import com.stockathings.StockaThings.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    boolean existsByTipoPagamento(String tipoPagamento);
}
