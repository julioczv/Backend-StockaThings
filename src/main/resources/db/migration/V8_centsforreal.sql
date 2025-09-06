-- itensvenda
UPDATE public.itensvenda
   SET itv_valor = itv_valor / 100.0
 WHERE (mod(itv_valor, 1) = 0)        -- sem casas decimais
   AND itv_valor >= 1000;             -- heurística: valores grandes

-- venda (se total também ficou em centavos)
UPDATE public.venda
   SET venda_total = venda_total / 100.0
 WHERE (mod(venda_total, 1) = 0)
   AND venda_total >= 1000;

-- produto (preço atual do produto)A
UPDATE public.produto
   SET produto_valorvenda = produto_valorvenda / 100.0
 WHERE (mod(produto_valorvenda, 1) = 0)
   AND produto_valorvenda >= 1000;

UPDATE public.produto
   SET produto_valorpago = produto_valorpago / 100.0
 WHERE (mod(produto_valorpago, 1) = 0)
   AND produto_valorpago >= 1000;
