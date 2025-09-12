package com.stockathings.StockaThings.domain.service;

import com.stockathings.StockaThings.domain.product.PageableDTO;
import com.stockathings.StockaThings.domain.product.Product;
import com.stockathings.StockaThings.domain.product.ProductRequestDTO;
import com.stockathings.StockaThings.domain.product.ProductResponseDTO;
import com.stockathings.StockaThings.domain.user.User;
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
    public ProductResponseDTO createProduct(ProductRequestDTO in, User me){
        var unidade = unidadeRepo.findById(in.unidadeMedidaId())
                .orElseThrow(() -> new RuntimeException("Unidade de medida não encontrada"));
        var categoria = categoriaRepo.findById(in.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        var product = new Product();
        product.setNomeProduto(in.nomeProduto());
        product.setDescricaoProduto(in.descricaoProduto());
        product.setValorPagoProduto(in.valorPagoProduto());
        product.setValorVendaProduto(in.valorVendaProduto());
        product.setQtdProduto(in.quantidadeProduto());

        product.setUnidadeMedida(unidade);
        product.setCategoria(categoria);


        product.setUsuario(me);

        var saved = repository.save(product);

        return ProductResponseDTO.from(saved);
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
                product.getCategoria().getNomeCategoria(),
                product.getUsuario().getId(),
                product.getUsuario().getNome()

        )).getContent();

        return new PageableDTO<>(
                productsPage.getTotalPages(),
                productsPage.getSize(),
                productsPage.getNumber(),
                productsPage.getTotalElements(),
                dtoList
        );
    }


    @Transactional
    public void deleteProduct(Long idProduto, User me) {
        int rows = repository.deleteByIdAndUsuarioId(idProduto, me.getId());
        if (rows == 0) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Produto não encontrado"
            );
        }
    }
}
