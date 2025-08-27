package com.stockathings.StockaThings.domain.user;

public record RegisterDTO(
        String login,

        String senha,

        String nome,

        String cnpj,

        String nomeSocial,

        String cidade,

        String estado,

        String cep,

        String endereco,

        String numEndereco,

        String bairro,

        String telefone) {
}
