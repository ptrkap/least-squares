package com.epam.linearregression;

import java.util.ArrayList;
import java.util.List;

public class LinearTransformation {

    public List<Double> transform(List<Double> days, Coefficients coefficients) {
        if (days.size() < 2) {
            throw new NotEnoughPointsException();
        }
        List<Double> regression = new ArrayList<>();
        for (Double day : days) {
            regression.add(coefficients.getA()*day + coefficients.getB());
        }
        return regression;
    }
}
