package com.epam.linearregression;

import java.util.Objects;

public class Point {

    private double day;
    private double dollarEuroRate;

    public Point(double day, double dollarEuroRate) {
        this.day = day;
        this.dollarEuroRate = dollarEuroRate;
    }

    public double getDay() {
        return day;
    }

    public double getDollarEuroRate() {
        return dollarEuroRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.day, day) == 0 &&
                Double.compare(point.dollarEuroRate, dollarEuroRate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, dollarEuroRate);
    }
}
