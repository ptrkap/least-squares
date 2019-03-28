package com.epam.linearregression;

class Coefficients {
    private double a;
    private double b;

    Coefficients(double a, double b) {
        this.a = a;
        this.b = b;
    }

    double getA() {
        return a;
    }

    double getB() {
        return b;
    }
}