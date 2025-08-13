/*CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE categoria
(
    categoria_id   SERIAL PRIMARY KEY,
    categoria_nome VARCHAR(100)
);

CREATE TABLE usuario
(
    usuario_id          UUID DEFAULT gen_random_uuid() PRIMARY KEY ,
    usuario_nome        VARCHAR(100) NOT NULL,
    usuario_cnpj        VARCHAR(100) NOT NULL,
    usuario_nome_social VARCHAR(100) NOT NULL,
    usuario_cep         VARCHAR(15),
    usuario_endereco    VARCHAR(100),
    usuario_numendereco VARCHAR(10),
    usuario_bairro      VARCHAR(100),
    usuario_fone        VARCHAR(100),
    usuario_email       VARCHAR(100),
    usuario_cidade      CHAR(40),
    usuario_estado      CHAR(20)
);

CREATE TABLE fornecedor
(
    fornecedor_id        UUID DEFAULT gen_random_uuid() PRIMARY KEY ,
    fornecedor_nome      VARCHAR(95),
    fornecedor_rsocial   VARCHAR(95),
    fornecedor_ie        VARCHAR(95),
    fornecedor_cnpj      VARCHAR(95),
    fornecedor_cep       VARCHAR(95),
    fornecedor_endereco  VARCHAR(95),
    fornecedor_bairro    VARCHAR(95),
    fornecedor_fone      VARCHAR(95),
    fornecedor_cel       VARCHAR(95),
    fornecedor_email     VARCHAR(95),
    fornecedor_endnumero VARCHAR(95),
    fornecedor_cidade    VARCHAR(95),
    fornecedor_estado    VARCHAR(95)
);*/

/*CREATE TABLE tipopagamento
(
    tipopag_id   SERIAL PRIMARY KEY,
    tipopag_nome VARCHAR(90) NOT NULL
);

CREATE TABLE unidmedida
(
    unidmed_id   SERIAL PRIMARY KEY,
    unidmed_nome VARCHAR(95)
);

CREATE TABLE produto
(
    produto_id         SERIAL PRIMARY KEY,
    produto_nome       VARCHAR(95),
    produto_descricao  TEXT,
    produto_valorpago  MONEY,
    produto_valorvenda MONEY,
    produto_qtde       INT,
    unidmed_id         INTEGER REFERENCES unidmedida (unidmed_id),
    categoria_id       INTEGER REFERENCES categoria (categoria_id)
);*/

/*CREATE TABLE compra
(
    compra_id        SERIAL PRIMARY KEY,
    compra_data      TIMESTAMP,
    compra_nfiscal   INTEGER,
    compra_total     MONEY,
    compra_nparcelas INTEGER,
    compra_status    VARCHAR(95),
    fornecedor_id     UUID DEFAULT gen_random_uuid() REFERENCES fornecedor (fornecedor_id),
    tipopag_id       INTEGER REFERENCES tipopagamento (tipopag_id)
);

CREATE TABLE venda
(
    venda_id        SERIAL PRIMARY KEY,
    venda_data      TIMESTAMP,
    venda_nfiscal   INTEGER,
    venda_total     MONEY,
    venda_nparcelas INTEGER,
    venda_status    VARCHAR(95),
    usuario_id       UUID DEFAULT gen_random_uuid() REFERENCES usuario (usuario_id),
    tipopag_id      INTEGER REFERENCES tipopagamento (tipopag_id),
    venda_avista    INTEGER
);

CREATE TABLE itenscompra
(
    itc_id    INTEGER,
    itc_qtde   INTEGER,
    itc_valor  MONEY,
    compra_id  INTEGER,
    produto_id INTEGER,
    PRIMARY KEY (itc_id, compra_id, produto_id),
    FOREIGN KEY (compra_id) REFERENCES compra (compra_id),
    FOREIGN KEY (produto_id) REFERENCES produto (produto_id)
);

CREATE TABLE itensvenda
(
    itv_id    INTEGER,
    itv_qtde   FLOAT,
    itv_valor  MONEY,
    venda_id   INTEGER,
    produto_id INTEGER,
    PRIMARY KEY (itv_id, venda_id, produto_id),
    FOREIGN KEY (venda_id) REFERENCES venda (venda_id),
    FOREIGN KEY (produto_id) REFERENCES produto (produto_id)
);





