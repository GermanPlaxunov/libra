package org.project.neural.process.config;

import org.project.data.services.interfaces.CoreStockService;
import org.project.data.services.interfaces.indicators.*;
import org.project.neural.process.training.dataset.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasetProvidersConfig {

    @Bean
    public DatasetProviders datasetProviders(ApoDatasetProvider apoDatasetProvider,
                                             BbandDatasetProvider bbandDatasetProvider,
                                             EmaDatasetProvider emaDatasetProvider,
                                             RsiDatasetProvider rsiDatasetProvider,
                                             SmaDatasetProvider smaDatasetProvider,
                                             StdDatasetProvider stdDatasetProvider) {
        return new DatasetProviders(apoDatasetProvider,
                bbandDatasetProvider,
                emaDatasetProvider,
                rsiDatasetProvider,
                smaDatasetProvider,
                stdDatasetProvider);
    }

    @Bean
    public ApoDatasetProvider apoDatasetProvider(
            AbsolutePriceOscillatorService absolutePriceOscillatorService,
            CoreStockService coreStockService) {
        return new ApoDatasetProvider(absolutePriceOscillatorService,
                coreStockService);
    }

    @Bean
    public BbandDatasetProvider bbandDatasetProvider(
            BollingerBandsService bollingerBandsService,
            CoreStockService coreStockService) {
        return new BbandDatasetProvider(bollingerBandsService,
                coreStockService);
    }

    @Bean
    public EmaDatasetProvider emaDatasetProvider(
            ExponentialMovingAverageService exponentialMovingAverageService,
            CoreStockService coreStockService) {
        return new EmaDatasetProvider(exponentialMovingAverageService,
                coreStockService);
    }

    @Bean
    public RsiDatasetProvider rsiDatasetProvider(
            RelativeStrengthIndicatorService relativeStrengthIndicatorService,
            CoreStockService coreStockService) {
        return new RsiDatasetProvider(relativeStrengthIndicatorService,
                coreStockService);
    }

    @Bean
    public SmaDatasetProvider smaDatasetProvider(
            SimpleMovingAverageService simpleMovingAverageService,
            CoreStockService coreStockService) {
        return new SmaDatasetProvider(simpleMovingAverageService,
                coreStockService);
    }

    @Bean
    public StdDatasetProvider stdDatasetProvider(
            StandardDerivativesService standardDerivativesService,
            CoreStockService coreStockService) {
        return new StdDatasetProvider(standardDerivativesService,
                coreStockService);
    }

}