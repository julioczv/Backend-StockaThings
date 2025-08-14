package com.stockathings.StockaThings.domain.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table( name = "categoria")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "categoria_id")
    private Long idCategoria;

    @Column(name = "categoria_nome", nullable = false, unique = true)
    private String nomeCategoria;

}
