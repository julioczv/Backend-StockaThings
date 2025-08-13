package com.stockathings.StockaThings.domain.service;

import com.stockathings.StockaThings.domain.category.Category;
import com.stockathings.StockaThings.domain.products.PageableDTO;
import com.stockathings.StockaThings.domain.products.Product;
import com.stockathings.StockaThings.domain.products.ProductRequestDTO;
import com.stockathings.StockaThings.domain.products.ProductResponseDTO;
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
import java.util.UUID;

@Service //Diz para a classe que ela é do tipo service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository repository;
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
        product.setQuantidadeProduto(data.quantidadeProduto());
        product.setUnidadeMedida(unidade);
        product.setCategoria((Category) categoria);


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
                product.getQuantidadeProduto(),
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


    public String deleteProduct(Long idProduto){
        repository.deleteById(idProduto);
        return "Produto deletado com sucesso !";
    }
}
