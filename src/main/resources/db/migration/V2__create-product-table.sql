CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE public.categoria
(
    categoria_id   serial4      NOT NULL,
    categoria_nome varchar(100) NOT NULL,
    CONSTRAINT categoria_pkey PRIMARY KEY (categoria_id),
    CONSTRAINT uk_categoria_nome UNIQUE (categoria_nome)
);

CREATE TABLE public.fornecedor
(
    fornecedor_id        uuid DEFAULT gen_random_uuid() NOT NULL,
    fornecedor_nome      varchar(95)                    NULL,
    fornecedor_rsocial   varchar(95)                    NULL,
    fornecedor_ie        varchar(95)                    NULL,
    fornecedor_cnpj      varchar(95)                    NULL,
    fornecedor_cep       varchar(95)                    NULL,
    fornecedor_endereco  varchar(95)                    NULL,
    fornecedor_bairro    varchar(95)                    NULL,
    fornecedor_fone      varchar(95)                    NULL,
    fornecedor_cel       varchar(95)                    NULL,
    fornecedor_email     varchar(95)                    NULL,
    fornecedor_endnumero varchar(95)                    NULL,
    fornecedor_cidade    varchar(95)                    NULL,
    fornecedor_estado    varchar(95)                    NULL,
    CONSTRAINT fornecedor_pkey PRIMARY KEY (fornecedor_id)
);


CREATE TABLE public.tipopagamento
(
    tipopag_id   serial4     NOT NULL,
    tipopag_nome varchar(90) NOT NULL,
    CONSTRAINT tipopagamento_pkey PRIMARY KEY (tipopag_id)
);

CREATE TABLE public.unidmedida
(
    unidmed_id   serial4     NOT NULL,
    unidmed_nome varchar(95) NULL,
    CONSTRAINT uk_unidmed_nome UNIQUE (unidmed_nome),
    CONSTRAINT unidmedida_pkey PRIMARY KEY (unidmed_id)
);


CREATE TABLE public.usuario
(
    usuario_id          uuid DEFAULT gen_random_uuid() NOT NULL,
    usuario_nome        varchar(100)                   NOT NULL,
    usuario_cnpj        varchar(100)                   NOT NULL,
    usuario_nome_social varchar(100)                   NOT NULL,
    usuario_cep         varchar(15)                    NULL,
    usuario_endereco    varchar(100)                   NULL,
    usuario_numendereco varchar(10)                    NULL,
    usuario_bairro      varchar(100)                   NULL,
    usuario_fone        varchar(100)                   NULL,
    usuario_email       varchar(100)                   NULL,
    usuario_cidade      bpchar(40)                     NULL,
    usuario_estado      bpchar(20)                     NULL,
    CONSTRAINT usuario_pkey PRIMARY KEY (usuario_id)
);


CREATE TABLE public.compra
(
    compra_id        serial4     NOT NULL,
    compra_data      timestamp   NULL,
    compra_nfiscal   int4        NULL,
    compra_total     money       NULL,
    compra_nparcelas int4        NULL,
    compra_status    varchar(95) NULL,
    fornecedor_id    uuid DEFAULT gen_random_uuid(),
    tipopag_id       int4        NULL,
    CONSTRAINT compra_pkey PRIMARY KEY (compra_id),
    CONSTRAINT compra_fornecedor_id_fkey FOREIGN KEY (fornecedor_id) REFERENCES public.fornecedor (fornecedor_id),
    CONSTRAINT compra_tipopag_id_fkey FOREIGN KEY (tipopag_id) REFERENCES public.tipopagamento (tipopag_id)
);


CREATE TABLE public.produto
(
    produto_id         serial4     NOT NULL,
    produto_nome       varchar(95) NULL,
    produto_descricao  text        NULL,
    produto_valorpago  numeric     NULL,
    produto_valorvenda numeric     NULL,
    produto_qtde       int4        NULL,
    unidmed_id         int4        NULL,
    categoria_id       int4        NULL,
    CONSTRAINT produto_pkey PRIMARY KEY (produto_id),
    CONSTRAINT produto_categoria_id_fkey FOREIGN KEY (categoria_id) REFERENCES public.categoria (categoria_id),
    CONSTRAINT produto_unidmed_id_fkey FOREIGN KEY (unidmed_id) REFERENCES public.unidmedida (unidmed_id)
);


CREATE TABLE public.venda
(
    venda_id        serial4     NOT NULL,
    venda_data      timestamp   NULL,
    venda_nfiscal   int4        NULL,
    venda_total     money       NULL,
    venda_nparcelas int4        NULL,
    venda_status    varchar(95) NULL,
    usuario_id      uuid DEFAULT gen_random_uuid(),
    tipopag_id      int4        NULL,
    venda_avista    int4        NULL,
    CONSTRAINT venda_pkey PRIMARY KEY (venda_id),
    CONSTRAINT venda_tipopag_id_fkey FOREIGN KEY (tipopag_id) REFERENCES public.tipopagamento (tipopag_id),
    CONSTRAINT venda_usuario_id_fkey FOREIGN KEY (usuario_id) REFERENCES public.usuario (usuario_id)
);


CREATE TABLE public.itenscompra
(
    itc_id     int4  NOT NULL,
    itc_qtde   int4  NULL,
    itc_valor  money NULL,
    compra_id  int4  NOT NULL,
    produto_id int4  NOT NULL,
    CONSTRAINT itenscompra_pkey PRIMARY KEY (itc_id, compra_id, produto_id),
    CONSTRAINT itenscompra_compra_id_fkey FOREIGN KEY (compra_id) REFERENCES public.compra (compra_id),
    CONSTRAINT itenscompra_produto_id_fkey FOREIGN KEY (produto_id) REFERENCES public.produto (produto_id)
);


CREATE TABLE public.itensvenda
(
    itv_id     int4   NOT NULL,
    itv_qtde   float8 NULL,
    itv_valor  money  NULL,
    venda_id   int4   NOT NULL,
    produto_id int4   NOT NULL,
    CONSTRAINT itensvenda_pkey PRIMARY KEY (itv_id, venda_id, produto_id),
    CONSTRAINT itensvenda_produto_id_fkey FOREIGN KEY (produto_id) REFERENCES public.produto (produto_id),
    CONSTRAINT itensvenda_venda_id_fkey FOREIGN KEY (venda_id) REFERENCES public.venda (venda_id)
);