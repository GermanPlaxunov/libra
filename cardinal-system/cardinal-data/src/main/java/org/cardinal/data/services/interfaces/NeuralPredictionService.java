package org.cardinal.data.services.interfaces;

import org.cardinal.data.entities.NeuralPredictionEntity;
import org.cardinal.model.Indicators;

import java.util.List;

public interface NeuralPredictionService {

    List<NeuralPredictionEntity> findPredictionsOverPeriod(String symbol, Indicators indicator, long periodSeconds);

    void save(NeuralPredictionEntity entity);

    NeuralPredictionEntity findLast(String symbol, Indicators indicator);
}
