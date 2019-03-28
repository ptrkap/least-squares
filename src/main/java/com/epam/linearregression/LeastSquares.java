package com.epam.linearregression;

import java.util.List;

public class LeastSquares {

    public Coefficients calculate(List<Point> points) {
        if (points.size() < 2) {
            throw new NotEnoughPointsException();
        }

        double daysMean = calculateDaysMean(points);
        double dollarEuroRatesMean = calculateDollarEuroRatesMean(points);


        double numerator = calculateNumerator(points, daysMean, dollarEuroRatesMean);
        double denominator = calculateDenominator(points, daysMean);

        double a = numerator/denominator;
        double b = dollarEuroRatesMean - a*daysMean;

        return new Coefficients(a ,b);
    }

    private double calculateNumerator(List<Point> points, double daysMean, double dollarEuroRatesMean) {
        double sum = 0;
        for (Point point : points) {
            sum += (point.getDay() - daysMean)*(point.getDollarEuroRate() - dollarEuroRatesMean);
        }
        return sum;
    }

    private double calculateDenominator(List<Point> points, double daysMean) {
        double sum = 0;
        for (Point point : points) {
            sum += Math.pow(point.getDay() - daysMean, 2);
        }
        return sum;
    }

    private double calculateDaysMean(List<Point> points) {
        int n = points.size();
        double sum = 0;
        for (Point point : points) {
            sum += point.getDay();
        }
        return sum/n;
    }

    private double calculateDollarEuroRatesMean(List<Point> points) {
        int n = points.size();
        double sum = 0;
        for (Point point : points) {
            sum += point.getDollarEuroRate();
        }
        return sum/n;
    }
}
