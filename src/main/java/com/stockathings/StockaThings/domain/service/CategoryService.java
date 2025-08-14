package com.stockathings.StockaThings.domain.service;

import com.stockathings.StockaThings.domain.category.Category;
import com.stockathings.StockaThings.domain.category.CategoryRequestDTO;
import com.stockathings.StockaThings.domain.category.CategoryResponseDTO;
import com.stockathings.StockaThings.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public Category createCategory(CategoryRequestDTO data){
        if(repository.existsByNomeCategoria(data.nomeCategoria())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Categoria já cadastrada"
            );
        } else {
            Category category = new Category();
            category.setNomeCategoria(data.nomeCategoria());
            repository.save(category);

            return category;
        }
    }

    public List<CategoryResponseDTO> getAllCategories(){
        return repository.findAll().
                stream().
                map( category ->
                new CategoryResponseDTO(category.getIdCategoria(), category.getNomeCategoria())).toList();
    }

    public String deleteCategory(Long idCategoria) {
        repository.deleteById(idCategoria);
        return "Categoria deletada com sucesso !";
    }
}
