package com.epam.linearregression;

public class Point {

    private double dollarEuroRate;
    private double day;

    public Point(double dollarEuroRate, double day) {
        this.dollarEuroRate = dollarEuroRate;
        this.day = day;
    }

    public double getDollarEuroRate() {
        return dollarEuroRate;
    }

    public double getDay() {
        return day;
    }
}
