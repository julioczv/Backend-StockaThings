package com.stockathings.StockaThings.domain.service;

import com.stockathings.StockaThings.domain.unitymeasure.UnityMeasure;
import com.stockathings.StockaThings.domain.unitymeasure.UnityMeasureRequestDTO;
import com.stockathings.StockaThings.domain.unitymeasure.UnityMeasureResponseDTO;
import com.stockathings.StockaThings.repositories.UnityMeasureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UnityMeasureService {

    private final UnityMeasureRepository repository;

    public UnityMeasure createUnity(UnityMeasureRequestDTO data) {
        if(repository.existsByUnidMedida(data.unidMedida())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Unidade de medida já cadastrada"
            );
        } else {
            UnityMeasure unityMeasure = new UnityMeasure();

            unityMeasure.setUnidMedida(data.unidMedida());

            repository.save(unityMeasure);

            return unityMeasure;
        }


    }

    public List<UnityMeasureResponseDTO> getAllUnities() {
        return repository.
                findAll().
                stream().
                map(
                unity ->
                new UnityMeasureResponseDTO(unity.getUnidadeMedidaId(), unity.getUnidMedida())).toList();
    }

    public String deleteUnity(Long unidadeMedidaId) {
        repository.deleteById(unidadeMedidaId);
        return "Unidade de medida deletada com sucesso !";
    }

}
