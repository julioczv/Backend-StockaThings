
ALTER TABLE itensvenda
  ADD COLUMN itv_custo NUMERIC(12,2) NOT NULL DEFAULT 0;

UPDATE itensvenda iv
SET itv_custo = p.produto_valorpago
FROM produto p
WHERE p.produto_id = iv.produto_id;

ALTER TABLE itensvenda ALTER COLUMN itv_custo DROP DEFAULT;

