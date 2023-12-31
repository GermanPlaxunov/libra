package org.project.neural.process.predictions.predictors;

import org.libra.data.services.interfaces.CoreStockService;
import org.libra.data.services.interfaces.NeuralPredictionService;
import org.libra.data.services.interfaces.ProcessParamsService;
import org.libra.data.services.interfaces.indicators.AbsolutePriceOscillatorService;
import org.project.model.Indicators;
import org.project.neural.process.network.NetworkStore;
import org.project.neural.process.predictions.Predictor;

public class ApoPredictor extends AbstractPredictor implements Predictor {

    private final AbsolutePriceOscillatorService absolutePriceOscillatorService;
    private final NetworkStore networkStore;

    public ApoPredictor(AbsolutePriceOscillatorService absolutePriceOscillatorService,
                        NeuralPredictionService neuralPredictionService,
                        ProcessParamsService processParamsService,
                        CoreStockService coreStockService,
                        NetworkStore networkStore) {
        super(neuralPredictionService, processParamsService,
                coreStockService);
        this.absolutePriceOscillatorService = absolutePriceOscillatorService;
        this.networkStore = networkStore;
    }

    /**
     * Makes prediction using the last APO
     * value and the last price delta.
     *
     * @param symbol - the name of the stock.
     * @return price change prediction.
     */
    @Override
    public Double predict(String symbol) {
        var network = networkStore.get(Indicators.APO, symbol);
        var apo = absolutePriceOscillatorService.findLast(symbol);
        var prevPriceChange = getPrevPriceChange(symbol);
        var prediction = network.predict(apo.getValue(), prevPriceChange);
        savePrediction(symbol, Indicators.APO, apo.getValue(), prediction);
        return prediction;
    }

}
