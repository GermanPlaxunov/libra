package org.project.core.core.process.decision.indicators.sma;

import lombok.RequiredArgsConstructor;
import org.project.core.client.NeuralClient;
import org.project.core.core.process.decision.indicators.IndicatorDecisionProcessor;
import org.project.core.core.process.vars.ProcessVars;

@RequiredArgsConstructor
public class SmaDecisionProcessor implements IndicatorDecisionProcessor {

    private final NeuralClient neuralClient;

    @Override
    public Long shouldPositionBeClosed(ProcessVars processVars) {
        return null;
    }

    @Override
    public Long shouldPositionBeOpen(ProcessVars processVars) {
        return null;
    }
}
