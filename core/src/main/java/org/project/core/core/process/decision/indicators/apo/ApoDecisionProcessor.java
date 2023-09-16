package org.project.core.core.process.decision.indicators.apo;

import lombok.RequiredArgsConstructor;
import org.project.core.client.NeuralClient;
import org.project.core.core.process.decision.indicators.IndicatorDecisionProcessor;
import org.project.core.core.process.vars.ProcessVars;

@RequiredArgsConstructor
public class ApoDecisionProcessor implements IndicatorDecisionProcessor {

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
