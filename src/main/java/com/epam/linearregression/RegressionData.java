package com.epam.linearregression;

import java.util.List;

public class RegressionData {

    List<Point> regressionPoints;
    List<Point> predictedPoints;

    public RegressionData(List<Point> regressionPoints, List<Point> predictedPoints) {
        this.regressionPoints = regressionPoints;
        this.predictedPoints = predictedPoints;
    }

    public List<Point> getRegressionPoints() {
        return regressionPoints;
    }

    public List<Point> getPredictedPoints() {
        return predictedPoints;
    }
}
