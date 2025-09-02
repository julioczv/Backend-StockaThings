-- venda_total
ALTER TABLE public.venda
  ALTER COLUMN venda_total TYPE numeric(12,2)
  USING
    CASE
      WHEN pg_typeof(venda_total)::text = 'money'
        THEN venda_total::numeric
      ELSE replace(replace(translate(venda_total::text, 'R$ .', ''), ',', '.'), ' ','')::numeric
    END;

-- itensvenda.itv_valor
ALTER TABLE public.itensvenda
  ALTER COLUMN itv_valor TYPE numeric(12,2)
  USING
    CASE
      WHEN pg_typeof(itv_valor)::text = 'money'
        THEN itv_valor::numeric
      ELSE replace(replace(translate(itv_valor::text, 'R$ .', ''), ',', '.'), ' ','')::numeric
    END;

-- produto.produto_valorvenda
ALTER TABLE public.produto
  ALTER COLUMN produto_valorvenda TYPE numeric(12,2)
  USING
    CASE
      WHEN pg_typeof(produto_valorvenda)::text = 'money'
        THEN produto_valorvenda::numeric
      ELSE replace(replace(translate(produto_valorvenda::text, 'R$ .', ''), ',', '.'), ' ','')::numeric
    END;

-- produto.produto_valorpago
ALTER TABLE public.produto
  ALTER COLUMN produto_valorpago TYPE numeric(12,2)
  USING
    CASE
      WHEN pg_typeof(produto_valorpago)::text = 'money'
        THEN produto_valorpago::numeric
      ELSE replace(replace(translate(produto_valorpago::text, 'R$ .', ''), ',', '.'), ' ','')::numeric
    END;

