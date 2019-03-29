package com.epam.linearregression;

import com.epam.Setup;

import java.util.List;

public class SamplesCalculator {
    private Setup setup;

    public SamplesCalculator(Setup setup) {
        this.setup = setup;
    }

    public int calculateSamplesNumberPerRegression() {
        return setup.getFrameInDays() * 24*60/setup.getIntervalInMinutes();
    }

    public int calculateLastStartPoint(List<Double> days, int samplesNumber) {
        return days.size() - 1 - samplesNumber;
    }
}
