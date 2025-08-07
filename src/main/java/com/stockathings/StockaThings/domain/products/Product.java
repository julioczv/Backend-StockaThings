package com.stockathings.StockaThings.domain.products;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "products") // Nome da nossa tabela no Postgree
@Entity //Quer dizer que tudo que esta dentro da classe vira uma instancia o entity precisa ter um @Id pra funcionar
@Getter // gera automaticamente os getters e os setters dentro de nossa classe
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id // Permite que o proprio banco crie o id, sem precisar passar por parametro
    @GeneratedValue(strategy = GenerationType.IDENTITY) // A estratégia GenerationType.IDENTITY faz com que o banco gere IDs auto-incrementais, como 1, 2, 3,
    private Long id;

    private String nameProduct;

    private String description;

    private BigDecimal costPrice;

    private BigDecimal sellingPrice;

    private Integer stockQuantity;

    private String type;

}
