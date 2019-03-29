package com.epam.linearregression;

import com.epam.Setup;
import java.util.List;

public class SamplesCalculator {

    private static final int HOURS_IN_DAY = 24;
    private static final int MINUTES_IN_HOUR = 60;
    private Setup setup;

    public SamplesCalculator(Setup setup) {
        this.setup = setup;
    }

    public int calculateSamplesNumberPerRegression() {
        return setup.getFrameInDays() * HOURS_IN_DAY * MINUTES_IN_HOUR / setup.getIntervalInMinutes();
    }

    public int calculateSamplesNumberPerRegressionOneDayAhead() {
        return (setup.getFrameInDays() + 1) * HOURS_IN_DAY * MINUTES_IN_HOUR / setup.getIntervalInMinutes();
    }

    public int calculateLastStartPoint(List<Double> days, int samplesNumber) {
        return days.size() - 1 - samplesNumber;
    }
}
