package com.stockathings.StockaThings.domain.payment;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="tipopagamento")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipopag_id")
    private Long idTipoPagamento;

    @Column(name = "tipopag_nome")
    private String tipoPagamento;
}
