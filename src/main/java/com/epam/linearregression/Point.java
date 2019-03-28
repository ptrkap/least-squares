package com.epam.linearregression;

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
}
