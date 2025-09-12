ALTER TABLE itensvenda
  ALTER COLUMN itv_qtde TYPE INTEGER USING round(itv_qtde)::integer;

ALTER TABLE itenscompra
  ALTER COLUMN itc_qtde TYPE INTEGER USING round(itc_qtde)::integer;
