package com.epam.linearregression;

import java.util.ArrayList;
import java.util.List;

public class RealRatesContainer {

    private List<Point> points = new ArrayList<>();

    public void add(String[] arguments) {
        points.add(new Point(Double.valueOf(arguments[0]), Double.valueOf(arguments[1])));
    }

    public List<Point> getPoints() {
        return points;
    }
}
