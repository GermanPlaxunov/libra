package org.libra.bragi.services.classes;

import lombok.RequiredArgsConstructor;
import org.libra.bragi.repositories.NeuralPredictionRepository;
import org.libra.bragi.services.interfaces.NeuralPredictionService;
import org.libra.bragi.entities.NeuralPredictionEntity;
import org.project.model.Indicators;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class NeuralPredictionServiceImpl implements NeuralPredictionService {

    private final NeuralPredictionRepository repository;

    @Override
    public List<NeuralPredictionEntity> findPredictionsOverPeriod(String symbol, Indicators indicator, long periodSeconds) {
        var last = repository.findFirstBySymbolAndIndicatorNameOrderByDateDesc(symbol, indicator.name())
                .orElse(null);
        if (last != null && last.getDate() != null) {
            var lastDate = last.getDate()
                    .minusSeconds(periodSeconds);
            return repository.findAllBySymbolAndIndicatorNameAndDateGreaterThanOrderByDateAsc(symbol, indicator.name(), lastDate);
        }
        return Collections.emptyList();
    }

    @Override
    public void save(NeuralPredictionEntity entity) {
        repository.saveAndFlush(entity);
    }

    @Override
    public NeuralPredictionEntity findLast(String symbol, Indicators indicator) {
        return repository.findFirstBySymbolAndIndicatorNameOrderByDateDesc(symbol, indicator.name())
                .orElse(null);
    }
}