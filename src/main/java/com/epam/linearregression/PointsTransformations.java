package com.epam.linearregression;

import java.util.ArrayList;
import java.util.List;

public class PointsTransformations {

    public List<Double> transformToDays(List<Point> realRatesPoints) {
        List<Double> days = new ArrayList<>();
        for (Point realRatePoint : realRatesPoints) {
            days.add(realRatePoint.getDay());
        }
        return days;
    }

    public List<Point> transformToPoints(List<Double> days, List<Double> regressionRates) {
        List<Point> regressionPoints = new ArrayList<>();
        for (int i = 0; i < days.size(); i++) {
            regressionPoints.add(new Point(days.get(i), regressionRates.get(i)));
        }
        return regressionPoints;
    }
}
