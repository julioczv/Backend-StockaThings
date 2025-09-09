package com.stockathings.StockaThings.domain.product;


import com.stockathings.StockaThings.domain.category.Category;
import com.stockathings.StockaThings.domain.unitymeasure.UnityMeasure;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "produto") // Nome da nossa tabela no Postgree
@Entity //Quer dizer que tudo que esta dentro da classe vira uma instancia o entity precisa ter um @Id pra funcionar
@Getter // gera automaticamente os getters e os setters dentro de nossa classe
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id // Permite que o proprio banco crie o id, sem precisar passar por parametro
    @GeneratedValue(strategy = GenerationType.IDENTITY) // A estratégia GenerationType.IDENTITY faz com que o banco gere IDs auto-incrementais, como 1, 2, 3,

    @Column(name = "produto_id")
    private Long idProduto;

    @Column(name = "produto_nome")
    private String nomeProduto;

    @Column(name = "produto_descricao")
    private String descricaoProduto;

    @Column(name = "produto_valorpago")
    private BigDecimal valorPagoProduto;

    @Column(name = "produto_valorvenda")
    private BigDecimal valorVendaProduto;

    @Column(name = "produto_qtde")
    private Integer qtdProduto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unidmed_id")
    private UnityMeasure unidadeMedida;

    @JoinColumn(name = "categoria_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category categoria;

}
