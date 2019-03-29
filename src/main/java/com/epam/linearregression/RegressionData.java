package com.epam.linearregression;

import java.util.List;

public class RegressionData {

    private Coefficients coefficients;
    private List<Point> regressionPoints;
    private List<Point> predictedPoints;

    public RegressionData(
            List<Point> regressionPoints,
            List<Point> predictedPoints) {
        this.regressionPoints = regressionPoints;
        this.predictedPoints = predictedPoints;
    }

    public List<Point> getRegressionPoints() {
        return regressionPoints;
    }

    public List<Point> getPredictedPoints() {
        return predictedPoints;
    }

    public void setCoefficients(Coefficients coefficients) {
        this.coefficients = coefficients;
    }

    public Coefficients getCoefficients() {
        return coefficients;
    }
}
