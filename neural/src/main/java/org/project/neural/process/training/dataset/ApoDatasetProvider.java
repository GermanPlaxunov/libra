package org.project.neural.process.training.dataset;

import lombok.RequiredArgsConstructor;
import org.libra.data.entities.CoreStockEntity;
import org.libra.data.entities.indicators.AbsolutePriceOscillatorEntity;
import org.libra.data.services.interfaces.ProcessParamsService;
import org.libra.data.services.interfaces.indicators.AbsolutePriceOscillatorService;
import org.project.model.Indicators;
import org.project.neural.process.training.dataset.delta.PriceChangeCalculator;
import org.project.neural.process.training.dataset.splitters.DataDateSplitter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ApoDatasetProvider implements DatasetProvider {

    private final AbsolutePriceOscillatorService absolutePriceOscillatorService;
    private final PriceChangeCalculator priceChangeCalculator;
    private final ProcessParamsService processParamsService;
    private final DataDateSplitter dataDateSplitter;

    /**
     * Should provide a list of Lists. Each list for APO contains:
     * priceChange - the price delta comparing previous datapoint;
     * apo - absolute price oscillator for each period;
     *
     * @param symbol - the name of the stock.
     * @param stocks - the list of the stocks.
     * @return dataset.
     */
    @Override
    public List<List<Double>> getData(String symbol, List<CoreStockEntity> stocks) {
        var cacheDepthSeconds = processParamsService.getTrainCacheDepth(symbol, Indicators.APO);
        var intervalSeconds = processParamsService.getTrainInterval(symbol, Indicators.APO);
        var allIndicators = absolutePriceOscillatorService.findCache(symbol, cacheDepthSeconds);
        var indicators = dataDateSplitter.split(allIndicators, intervalSeconds);
        var filteredStocks = dataDateSplitter.split(stocks, intervalSeconds);
        var priceChanges = priceChangeCalculator.getPriceChanges(filteredStocks);
        return map(indicators, priceChanges);
    }

    /**
     * Maps two lists in list of maps.
     *
     * @param apos        - list of apos.
     * @param priceChange - list of price deltas.
     * @return list of maps with data.
     */
    private List<List<Double>> map(List<AbsolutePriceOscillatorEntity> apos,
                                   List<Double> priceChange) {
        var counter = Math.min(apos.size(), priceChange.size());
        var result = new ArrayList<List<Double>>();
        for (var i = 0; i < counter; i++) {
            var point = new ArrayList<Double>();
            point.add(priceChange.get(i));
            point.add(apos.get(i).getValue());
            result.add(point);
        }
        return result;
    }

}
