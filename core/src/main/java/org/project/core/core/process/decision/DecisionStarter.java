package org.project.core.core.process.decision;

import lombok.RequiredArgsConstructor;
import org.libra.decision.processor.indicators.IndicatorProcessorsStore;
import org.project.model.CoreStock;
import org.project.model.Indicators;
import org.project.model.ProcessVars;
import org.project.model.decision.Decision;

@RequiredArgsConstructor
public class DecisionStarter {

    private final BuyAmountCurrencyProcessor buyAmountCurrencyProcessor;
    private final IndicatorProcessorsStore indicatorProcessorsStore;

    /**
     * Returns data about decision and amount of
     * stocks should be bought.
     *
     * @return decision.
     */
    public void ifNewPositionShouldBeOpened(ProcessVars<CoreStock> processVars) {
        Double finalScore = 0.0;
        for (var indicator : Indicators.values()) {
            var processor = indicatorProcessorsStore.get(indicator);
            finalScore += processor.checkOpenNewPosition(processVars);
        }
        processVars.setScore(finalScore);
        processVars.setAmountCurr(buyAmountCurrencyProcessor.getBuyAmountCurrency(processVars));
        processVars.setDecision(evaluateDecision(finalScore, true));
    }

    /**
     * Returns decision whether current position
     * should be closed.
     *
     * @return decision.
     */
    public void ifCurrentPositionShouldBeClosed(ProcessVars<CoreStock> processVars) {
        Double finalScore = 0.0;
        for (var indicator : Indicators.values()) {
            var processor = indicatorProcessorsStore.get(indicator);
            finalScore += processor.checkCloseCurrentPosition(processVars);
        }
        processVars.setScore(finalScore)
                .setDecision(evaluateDecision(finalScore, false));
    }

    private Decision evaluateDecision(double score, boolean isOpeningNewPosition) {
        if (score > 5000.0) {
            return isOpeningNewPosition
                    ? Decision.DECISION_OPEN_NEW
                    : Decision.DECISION_CLOSE_CURRENT;
        }
        return Decision.DECISION_WAIT;
    }

}
