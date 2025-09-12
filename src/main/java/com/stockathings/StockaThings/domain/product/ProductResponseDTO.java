package com.stockathings.StockaThings.domain.product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(
        Long idProduto,
        String nomeProduto,
        String descricaoProduto,
        BigDecimal valorPagoProduto,
        BigDecimal valorVendaProduto,
        Integer qtdProduto,
        Long unidmedId,
        String unidmedNome,
        Long categoriaId,
        String categoriaNome,
        UUID usuarioId,
        String nomeEmpresa
) {
    public static ProductResponseDTO from(Product p) {
        return new ProductResponseDTO(
                p.getIdProduto(),
                p.getNomeProduto(),
                p.getDescricaoProduto(),
                p.getValorPagoProduto(),
                p.getValorVendaProduto(),
                p.getQtdProduto(),
                p.getUnidadeMedida().getIdUnidMedida(),
                p.getUnidadeMedida().getUnidMedida(),
                p.getCategoria().getIdCategoria(),
                p.getCategoria().getNomeCategoria(),
                p.getUsuario() != null ? p.getUsuario().getId() : null,
                p.getUsuario().getNome()
        );
    }
}

