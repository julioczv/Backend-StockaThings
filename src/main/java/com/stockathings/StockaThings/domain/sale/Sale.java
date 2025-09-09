package com.stockathings.StockaThings.domain.sale;


import com.stockathings.StockaThings.domain.payment.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "venda")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venda_id")
    private Long idVenda;

    @Column(name = "venda_data")
    private LocalDateTime dataVenda;

    @Column(name = "venda_total")
    private BigDecimal totalVenda;

    @JoinColumn(name = "tipopag_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Payment tipoPagamento;
}
