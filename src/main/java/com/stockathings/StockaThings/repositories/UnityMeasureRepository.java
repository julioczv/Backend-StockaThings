package com.stockathings.StockaThings.repositories;

import com.stockathings.StockaThings.domain.unitymeasure.UnityMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnityMeasureRepository extends JpaRepository<UnityMeasure,Long> {
    boolean existsByUnidMedida(String unidMedida);
}
