package com.stockathings.StockaThings.controllers;


import com.stockathings.StockaThings.domain.products.Product;
import com.stockathings.StockaThings.domain.products.ProductRequestDTO;
import com.stockathings.StockaThings.domain.service.UnityMeasureService;
import com.stockathings.StockaThings.domain.unitymeasure.UnityMeasure;
import com.stockathings.StockaThings.domain.unitymeasure.UnityMeasureRequestDTO;
import com.stockathings.StockaThings.domain.unitymeasure.UnityMeasureResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidademedidas")
public class UnityMeasureController {

    @Autowired
    private UnityMeasureService unityService;

    @PostMapping
    public ResponseEntity<UnityMeasure> createUnity(@RequestBody UnityMeasureRequestDTO body) {
        UnityMeasure newUnityMeasure = this.unityService.createUnity(body);
        return ResponseEntity.ok(newUnityMeasure);
    }

    @GetMapping
    public ResponseEntity<List<UnityMeasureResponseDTO>> getAllUnities() {
        List<UnityMeasureResponseDTO> allUnityMeasure = unityService.getAllUnities();
        return ResponseEntity.ok(allUnityMeasure);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long idUnidMedida) {
        unityService.deleteUnity(idUnidMedida);
        return ResponseEntity.ok("Produto deletado com sucesso !");
    }
}
