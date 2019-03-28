package com.epam.linearregression;

public class Predictor {

    public double predict(Coefficients coefficients, double day) {
        return coefficients.getA()*day + coefficients.getB();
    }
}
