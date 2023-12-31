package org.project.neural.process.predictions.predictors;

import org.libra.data.services.interfaces.CoreStockService;
import org.libra.data.services.interfaces.NeuralPredictionService;
import org.libra.data.services.interfaces.ProcessParamsService;
import org.libra.data.services.interfaces.indicators.StandardDerivativesService;
import org.project.model.Indicators;
import org.project.neural.process.network.NetworkStore;
import org.project.neural.process.predictions.Predictor;

public class StdPredictor extends AbstractPredictor implements Predictor {

    private final StandardDerivativesService standardDerivativesService;
    private final NetworkStore networkStore;

    public StdPredictor(StandardDerivativesService standardDerivativesService,
                        NeuralPredictionService neuralPredictionService,
                        ProcessParamsService processParamsService,
                        CoreStockService coreStockService,
                        NetworkStore networkStore) {
        super(neuralPredictionService, processParamsService,
                coreStockService);
        this.standardDerivativesService = standardDerivativesService;
        this.networkStore = networkStore;
    }

    @Override
    public Double predict(String symbol) {
        var network = networkStore.get(Indicators.STD, symbol);
        var std = standardDerivativesService.findLast(symbol);
        var prevPriceChange = getPrevPriceChange(symbol);
        var prediction = network.predict(std.getValue(), prevPriceChange);
        savePrediction(symbol, Indicators.STD, std.getValue(), prediction);
        return prediction;
    }
}
