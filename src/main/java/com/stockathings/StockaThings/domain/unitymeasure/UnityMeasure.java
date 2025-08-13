package com.stockathings.StockaThings.domain.unitymeasure;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "unidmedida")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnityMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "unidmed_id")
    private Long idUnidMedida;

    @Column(name = "unidmed_nome", nullable = false, unique = true)
    private String unidMedida;
}
