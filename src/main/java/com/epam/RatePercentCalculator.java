package com.epam;

import com.epam.linearregression.Point;

import java.util.List;

public class RatePercentCalculator {

    private double rateToday;
    private double rateTomorrow;

    public RatePercentCalculator(List<Point> realRatesPoints, double rateTomorrow) {
        this.rateToday = realRatesPoints.get(realRatesPoints.size() - 1).getDollarEuroRate();
        this.rateTomorrow = rateTomorrow;
    }

    public String calculatePercent() {
        double diff = rateTomorrow - rateToday;
        double percent = 100*diff/rateToday;
        String percentString = String.format("%.2f", percent).replace(",", ".") + "%";
        if (percent > 0) {
            percentString = "+" + percentString;
        }
        return percentString;
    }
}
