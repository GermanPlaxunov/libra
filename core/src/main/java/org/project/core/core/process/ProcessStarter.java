package org.project.core.core.process;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.core.core.market.MarketDataProvider;
import org.project.core.core.process.data.trend.TrendProvider;
import org.project.core.core.process.deal.DealMaker;
import org.project.core.core.process.indicators.IndicatorsCollector;
import org.project.core.core.process.strategy.MainStrategy;
import org.project.core.mapper.StockMapper;
import org.project.data.services.interfaces.CoreStockService;
import org.project.data.services.interfaces.PositionService;
import org.project.data.services.interfaces.ProcessParamsService;
import org.project.model.CoreStock;
import org.project.model.ProcessVars;

@Slf4j
@RequiredArgsConstructor
public class ProcessStarter {

    private final ProcessParamsService processParamsService;
    private final IndicatorsCollector indicatorsCollector;
    private final MarketDataProvider marketDataProvider;
    private final CoreStockService coreStockService;
    private final PositionService positionService;
    private final TrendProvider trendProvider;
    private final MainStrategy mainStrategy;
    private final StockMapper stockMapper;
    private final DealMaker dealMaker;

    /**
     * Entry point of process.
     * Collect all process data and launch
     * the strategy.
     *
     * @param symbol - the name of the stock.
     */
    public void startProcess(String symbol) {
        var next = marketDataProvider.getNextDataPoint(symbol);
        log.info("Received stock: {}", next);
        if (coreStockService.checkCacheExists(symbol)) {
            var processVars = new ProcessVars<CoreStock>();
            var cacheDepth = processParamsService.getMaximumCacheDepth(symbol);
            var coreStocks = coreStockService.findCache(symbol, cacheDepth);
            var stocks = stockMapper.mapAllToCore(coreStocks);
            processVars.setStocks(stocks);
            indicatorsCollector.collect(symbol, processVars);
            processVars.setTrendData(trendProvider.getTrend(symbol, stocks));
            launchStrategy(processVars);
        } else {
            log.info("Not enough cache data for {}", symbol);
        }
    }

    /**
     * Launch Root strategy of decision.
     * Should be called after all process
     * data collected in the processVars.
     *
     * @param processVars - all process data.
     */
    private void launchStrategy(ProcessVars processVars) {
        var symbol = processVars.getSymbol();
        if (positionService.ifOpenPosition(symbol)) {
            var result = mainStrategy.ifCurrentPositionShouldBeClosed(processVars);
            if (result.isShouldCurrentPositionBeClosed()) {
                dealMaker.closeLongPosition(symbol);
            }
        } else {
            var result = mainStrategy.ifNewPositionShouldBeOpened(processVars);
            if (result.isShouldNewPositionBeOpen()) {
                dealMaker.openLongPosition(symbol, result.getAmount());
            }
        }
    }
}
