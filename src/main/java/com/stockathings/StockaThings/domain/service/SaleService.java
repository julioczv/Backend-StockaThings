package com.stockathings.StockaThings.domain.service;

import com.stockathings.StockaThings.domain.sale.*;
import com.stockathings.StockaThings.domain.saleitem.SaleItem;
import com.stockathings.StockaThings.domain.saleitem.SaleItemResponseDTO;
import com.stockathings.StockaThings.domain.user.User;
import com.stockathings.StockaThings.repositories.PaymentRepository;
import com.stockathings.StockaThings.repositories.ProductRepository;
import com.stockathings.StockaThings.repositories.SaleItemRepository;
import com.stockathings.StockaThings.repositories.SaleRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    public SaleResponseDTO createSale(SaleRequestDTO in, User me) {

        if (in.items() == null || in.items().isEmpty()) {
            throw new IllegalArgumentException("Por favor selecione pelo menos um item");
        }

        var pm = pmRepository.findById(in.tipoPagamentoId())
                .orElseThrow(() -> new RuntimeException("Método de pagamento não encontrado"));

        Sale sale = new Sale();

        sale.setDataVenda(LocalDateTime.now());

        sale.setTipoPagamento(pm);
        sale.setUsuario(me);

        sale.setTotalVenda(BigDecimal.ZERO);

        saleRepository.save(sale);


        var saleItem = new ArrayList<SaleItemResponseDTO>();
        BigDecimal totalVenda = BigDecimal.ZERO;
        BigDecimal totalCompra = BigDecimal.ZERO;
        int totalItens = 0;

        for (var item : in.items()) {
            var product = productRepository.findById(item.idProduto())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + item.idProduto()));

            var qtd = Objects.requireNonNullElse(item.qtd(), 0);

            if (qtd <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");

            if (product.getQtdProduto() < qtd)
                throw new IllegalArgumentException("Estoque insuficiente do produto: " + product.getNomeProduto());

            // vendas
            BigDecimal unitSale = product.getValorVendaProduto();
            BigDecimal subtotalSale = unitSale.multiply(BigDecimal.valueOf(qtd));

            //custos
            BigDecimal unitBuy = product.getValorPagoProduto();
            BigDecimal subtotalBuy = unitBuy.multiply(BigDecimal.valueOf(qtd));

            var si = new SaleItem();
            si.setVenda(sale);
            si.setProduto(product);
            si.setQuantidade(qtd);
            si.setPrecoUnitario(unitSale);
            si.setPrecoCustoUnitario(unitBuy);
            itemRepository.save(si);

            //Baixa o estoque
            product.setQtdProduto(product.getQtdProduto() - qtd);

            saleItem.add(new SaleItemResponseDTO(
                    si.getIdItemVenda(), product.getIdProduto(), product.getNomeProduto(), qtd,
                    unitSale, subtotalSale, unitBuy, subtotalBuy
            ));

            totalVenda = totalVenda.add(subtotalSale);
            totalCompra = totalCompra.add(subtotalBuy);
            totalItens += qtd;
        }

        sale.setTotalVenda(totalVenda);
        saleRepository.save(sale);

        var totals = new SaleTotalDTO(totalItens, totalVenda, totalCompra);

        var tipoPagamento = sale.getTipoPagamento().getTipoPagamento();

        return new SaleResponseDTO(
                sale.getIdVenda(),
                sale.getDataVenda(),
                tipoPagamento,
                saleItem,
                totals
        );
    }

   /* @Transactional
    public List<SaleResponseDTO> findAllSales() {
        var sales = saleRepository.findAll();
        if (sales.isEmpty()) return List.of();

        var ids = sales.stream().map(Sale::getIdVenda).toList();
        var allItems = itemRepository.findByVendaIdInFetchProduct(ids);

        var itemsBySale = allItems.stream().collect(Collectors.groupingBy(
                si -> si.getVenda().getIdVenda(),
                Collectors.mapping(si -> {
                    int qtd = si.getQuantidade();
                    BigDecimal unitSale = si.getPrecoCustoUnitario();
                    BigDecimal subSale  = unitSale.multiply(BigDecimal.valueOf(qtd));
                    BigDecimal unitBuy  = si.getPrecoCustoUnitario();
                    BigDecimal subBuy   = unitBuy.multiply(BigDecimal.valueOf(qtd));
                    return new SaleItemResponseDTO(
                            si.getIdItemVenda(), si.getProduto().getIdProduto(), si.getProduto().getNomeProduto(),
                            qtd, unitSale, subSale, unitBuy, subBuy
                    );
                }, Collectors.toList())
        ));

        return sales.stream().map(sale -> {
            var items = itemsBySale.getOrDefault(sale.getIdVenda(), List.of());
            BigDecimal totalVenda  = sale.getTotalVenda();
            BigDecimal totalCompra = items.stream()
                    .map(SaleItemResponseDTO::subTotalPago)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            int totalItens = items.stream().mapToInt(SaleItemResponseDTO::quantidade).sum();

            var totals = new SaleTotalDTO(totalItens, totalVenda, totalCompra);

            return new SaleResponseDTO(
                    sale.getIdVenda(),
                    sale.getDataVenda(),
                    sale.getTipoPagamento().getTipoPagamento(),
                    items,
                    totals
            );
        }).toList();
    }*/


    @Transactional
    public SalePeriodDTO findByDateRange(LocalDate from, LocalDate to) {
        if (from == null || to == null) throw new IllegalArgumentException("from e to são obrigatórios");
        if (to.isBefore(from)) throw new IllegalArgumentException("data final não pode ser anterior à inicial");

        var start = from.atStartOfDay();
        var endExclusive = to.plusDays(1).atStartOfDay();

        var sales = saleRepository.findAllByPeriodoFetchPayment(start, endExclusive);
        if (sales.isEmpty()) {
            return new SalePeriodDTO(
                    new SalePeriodSummaryDTO(from, to, 0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO),
                    List.of()
            );
        }

        var saleIds  = sales.stream().map(Sale::getIdVenda).toList();
        var allItems = itemRepository.findByVendaIdInFetchProduct(saleIds);

        var itemsBySale = allItems.stream().collect(Collectors.groupingBy(
                si -> si.getVenda().getIdVenda(),
                Collectors.mapping(si -> {
                    int qtd = si.getQuantidade();
                    BigDecimal unitSale = si.getPrecoUnitario();
                    BigDecimal subSale  = unitSale.multiply(BigDecimal.valueOf(qtd));

                    BigDecimal unitBuy  = si.getProduto().getValorPagoProduto();
                    BigDecimal subBuy   = unitBuy.multiply(BigDecimal.valueOf(qtd));

                    return new SaleItemResponseDTO(
                            si.getIdItemVenda(), si.getProduto().getIdProduto(), si.getProduto().getNomeProduto(),
                            qtd, unitSale, subSale, unitBuy, subBuy
                    );
                }, Collectors.toList())
        ));

        var saleDtos = sales.stream().map(sale -> {
            var items = itemsBySale.getOrDefault(sale.getIdVenda(), List.of());

            BigDecimal faturado = items.stream()
                    .map(SaleItemResponseDTO::subTotalVenda)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal custo = items.stream()
                    .map(SaleItemResponseDTO::subTotalPago)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal lucro = faturado.subtract(custo);
            int totalItens   = items.stream().mapToInt(SaleItemResponseDTO::quantidade).sum();

            var totals = new SalesRangeTotalDTO(totalItens, custo, faturado, lucro);

            return new SalesRangeResponseDTO(
                    sale.getIdVenda(),
                    sale.getDataVenda(),
                    sale.getTipoPagamento().getTipoPagamento(),
                    items,
                    totals
            );
        }).toList();

        int totalItensPeriodo = saleDtos.stream().mapToInt(d -> d.totals().totalItens()).sum();
        BigDecimal custoPeriodo = saleDtos.stream()
                .map(d -> d.totals().custo())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal faturadoPeriodo = saleDtos.stream()
                .map(d -> d.totals().faturado())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal lucroPeriodo = faturadoPeriodo.subtract(custoPeriodo);

        var summary = new SalePeriodSummaryDTO(from, to, totalItensPeriodo, custoPeriodo, faturadoPeriodo, lucroPeriodo);

        return new SalePeriodDTO(summary, saleDtos);
    }

    @Transactional
    public void deleteSale(Long saleId, User me) {
        boolean pertence = saleRepository.existsByIdVendaAndUsuario_Id(saleId, me.getId());

        if(!pertence) {
            if(saleRepository.existsById(saleId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Essa venda não existe no seu banco de dados");
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrada");
        }

        var itens = itemRepository.findByVendaIdFetchProductForUpdate(saleId);

        for(var si : itens) {
            int qtd = si.getQuantidade();
            int rows = productRepository.addStock(si.getProduto().getIdProduto(), me.getId(), qtd);
            if(rows == 0){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Não foi possível estornar o estoque do produto" + si.getProduto().getNomeProduto());
            }
        }
        int del = saleRepository.deleteByIdAndUsuarioId(saleId, me.getId());
        if(del == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao excluir a venda");
        }
    }


}

