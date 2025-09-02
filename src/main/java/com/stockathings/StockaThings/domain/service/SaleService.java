package com.stockathings.StockaThings.domain.service;

import com.stockathings.StockaThings.domain.sale.Sale;
import com.stockathings.StockaThings.domain.sale.SaleRequestDTO;
import com.stockathings.StockaThings.domain.sale.SaleResponseDTO;
import com.stockathings.StockaThings.domain.sale.SaleTotalDTO;
import com.stockathings.StockaThings.domain.saleitem.SaleItem;
import com.stockathings.StockaThings.domain.saleitem.SaleItemResponseDTO;
import com.stockathings.StockaThings.repositories.PaymentRepository;
import com.stockathings.StockaThings.repositories.ProductRepository;
import com.stockathings.StockaThings.repositories.SaleItemRepository;
import com.stockathings.StockaThings.repositories.SaleRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final PaymentRepository pmRepository;
    private final SaleItemRepository itemRepository;
    private final ProductRepository productRepository;


    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO data) {

        if (data.items() == null || data.items().isEmpty()) {
            throw new IllegalArgumentException("Por favor selecione pelo menos um item");
        }

        var pm = pmRepository.findById(data.tipoPagamentoId())
                .orElseThrow(() -> new RuntimeException("Método de pagamento não encontrado"));

        Sale sale = new Sale();

        sale.setDataVenda(LocalDateTime.now());

        sale.setTipoPagamento(pm);

        sale.setTotalVenda(BigDecimal.ZERO);

        saleRepository.save(sale);


        var saleItem = new ArrayList<SaleItemResponseDTO>();
        BigDecimal totalVenda = BigDecimal.ZERO;
        BigDecimal totalCompra = BigDecimal.ZERO;
        int totalItens = 0;

        for (var item : data.items()) {
            var product = productRepository.findById(item.idProduto())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + item.idProduto()));

            var qtd = Objects.requireNonNullElse(item.qtd(), 0);

            if (qtd <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");

            if (product.getQtdProduto() < qtd)
                throw new IllegalArgumentException("Estoque insuficiente do produto: " + product.getNomeProduto());

            BigDecimal unitSale = product.getValorVendaProduto();
            BigDecimal subtotalSale = unitSale.multiply(BigDecimal.valueOf(qtd));
            BigDecimal unitBuy = product.getValorPagoProduto();
            BigDecimal subtotalBuy = unitBuy.multiply(BigDecimal.valueOf(qtd));

            var si = new SaleItem();
            si.setVenda(sale);
            si.setProduto(product);
            si.setQuantidade(qtd);
            si.setPrecoUnitario(unitSale);
            itemRepository.save(si);

            //Baixa o estoque
            product.setQtdProduto(product.getQtdProduto() - qtd);

            saleItem.add(new SaleItemResponseDTO(
                    si.getIdItemVenda(), product.getIdProduto(), product.getNomeProduto(), qtd,
                    unitSale, subtotalSale, unitBuy, subtotalBuy
            ));

            totalVenda = totalVenda.add(subtotalSale);
            totalItens = totalItens + qtd;

            totalCompra = totalCompra.add(subtotalBuy);
        }

        sale.setTotalVenda(totalVenda);
        saleRepository.save(sale);

        var totals = new SaleTotalDTO(totalItens, totalVenda, totalCompra);

        return new SaleResponseDTO(
                sale.getIdVenda(),
                sale.getDataVenda(),
                sale.getTipoPagamento().toString(),
                saleItem,
                totals
        );
    }

    @Transactional
    public List<SaleResponseDTO> findAllSales() {
        // 1) vendas + payment (sem N+1)
        var sales = saleRepository.findAll();
        if (sales.isEmpty()) return List.of();

        // 2) pega todos os itens dessas vendas
        var ids = sales.stream().map(Sale::getIdVenda).toList();
        var allItems = itemRepository.findByVendaIdInFetchProduct(ids);

        // 3) agrupa itens por venda
        Map<Long, List<SaleItemResponseDTO>> itemsBySale =
                allItems.stream().collect(Collectors.groupingBy(
                        si -> si.getVenda().getIdVenda(),
                        Collectors.mapping(
                                si -> new SaleItemResponseDTO(
                                        si.getIdItemVenda(),
                                        si.getProduto().getIdProduto(),
                                        si.getProduto().getNomeProduto(),
                                        si.getQuantidade(),
                                        si.getPrecoUnitario(),
                                        si.getPrecoUnitario().multiply(BigDecimal.valueOf(si.getQuantidade())),
                                        si.getProduto().getValorPagoProduto(),
                                        si.getProduto().getValorPagoProduto().multiply(BigDecimal.valueOf(si.getQuantidade()))
                                ),
                                Collectors.toList()
                        )
                ));

        return sales.stream().map(sale -> {
            var items = itemsBySale.getOrDefault(sale.getIdVenda(), List.of());


            BigDecimal totalVenda = sale.getTotalVenda();

            int totalItens = items.stream().mapToInt(SaleItemResponseDTO::quantidade).sum();

            var totalBuy = items.stream().map(SaleItemResponseDTO::subTotalPago).reduce(BigDecimal.ZERO, BigDecimal::add);

            var totals = new SaleTotalDTO(totalItens, totalBuy, totalVenda);

            String tipoPagamento = sale.getTipoPagamento().getTipoPagamento();

            return new SaleResponseDTO(
                    sale.getIdVenda(),
                    sale.getDataVenda(),
                    tipoPagamento,
                    items,
                    totals
            );
        }).toList();
    }
}

