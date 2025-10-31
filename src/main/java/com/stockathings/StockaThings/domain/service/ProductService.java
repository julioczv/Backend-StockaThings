package com.stockathings.StockaThings.domain.service;

import com.stockathings.StockaThings.domain.product.PageableDTO;
import com.stockathings.StockaThings.domain.product.Product;
import com.stockathings.StockaThings.domain.product.ProductRequestDTO;
import com.stockathings.StockaThings.domain.product.ProductResponseDTO;
import com.stockathings.StockaThings.repositories.CategoryRepository;
import com.stockathings.StockaThings.repositories.ProductRepository;
import com.stockathings.StockaThings.repositories.UnityMeasureRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Diz para a classe que ela é do tipo service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final UnityMeasureRepository unidadeRepo;
    private final CategoryRepository categoriaRepo;

    @Transactional
    public Product createProduct(ProductRequestDTO data /*Aqui chamados a nossa DTO de data*/){ //Passamos a nossa DTO para mapeala
        Product product = new Product(); //Instanciamos a classe para a poder receber os campos

        var unidade = unidadeRepo.findById(data.unidadeMedidaId())
                .orElseThrow(() -> new RuntimeException("Unidade de medida não encontrada"));

        var categoria = categoriaRepo.findById(data.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        product.setNomeProduto(data.nomeProduto());
        product.setDescricaoProduto(data.descricaoProduto());
        product.setValorPagoProduto(data.valorPagoProduto());
        product.setValorVendaProduto(data.valorVendaProduto());
        product.setQtdProduto(data.quantidadeProduto());
        product.setUnidadeMedida(unidade);
        product.setCategoria(categoria);


        repository.save(product);

        return product;
    }

    public PageableDTO<ProductResponseDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = repository.findAll(pageable);

        List<ProductResponseDTO> dtoList = productsPage.map(product -> new ProductResponseDTO(
                product.getIdProduto(),
                product.getNomeProduto(),
                product.getDescricaoProduto(),
                product.getValorPagoProduto(),
                product.getValorVendaProduto(),
                product.getQtdProduto(),
                product.getUnidadeMedida().getIdUnidMedida(),
                product.getUnidadeMedida().getUnidMedida(),
                product.getCategoria().getIdCategoria(),
                product.getCategoria().getNomeCategoria()

        )).getContent();

        return new PageableDTO<>(
                productsPage.getTotalPages(),
                productsPage.getSize(),
                productsPage.getNumber(),
                productsPage.getTotalElements(),
                dtoList
        );
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getProduct(Long idProduto){
        Product product = repository.findById(idProduto).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return toDto(product);
    }

    private ProductResponseDTO toDto(Product product) {
        return new ProductResponseDTO(
                product.getIdProduto(),
                product.getNomeProduto(),
                product.getDescricaoProduto(),
                product.getValorPagoProduto(),
                product.getValorVendaProduto(),
                product.getQuantidadeProduto(),
                product.getUnidadeMedida().getIdUnidMedida(),
                product.getUnidadeMedida().getUnidMedida(),
                product.getCategoria().getIdCategoria(),
                product.getCategoria().getNomeCategoria()
        );
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long idProduto, ProductRequestDTO data) {
        Product product = repository.findById(idProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        var unidade = unidadeRepo.findById(data.unidadeMedidaId())
                .orElseThrow(() -> new RuntimeException("Unidade de medida não encontrada"));

        var categoria = categoriaRepo.findById(data.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        product.setNomeProduto(data.nomeProduto());
        product.setDescricaoProduto(data.descricaoProduto());
        product.setValorPagoProduto(data.valorPagoProduto());
        product.setValorVendaProduto(data.valorVendaProduto());
        product.setQuantidadeProduto(data.quantidadeProduto());
        product.setUnidadeMedida(unidade);
        product.setCategoria(categoria);

        repository.save(product);
        return toDto(product);
    }

    public String deleteProduct(Long idProduto){
        repository.deleteById(idProduto);
        return "Produto deletado com sucesso !";
    }
}
