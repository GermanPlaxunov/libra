package org.project.core.market;

import org.project.model.MarketStock;

import java.time.LocalDateTime;

public interface MarketClient {

    MarketStock getNextStock(String stockName, LocalDateTime prevStockDate);

    Long openLongPosition(String stockName, Double amountCurr);

    void closeLongPosition(String stockName);

}