package com.epam

import com.epam.linearregression.Point
import spock.lang.Specification

class RatePercentCalculatorTest extends Specification {

    def "should calculate percent based on rate today (0.88) and rate tomorrow (0.91)"() {
        given:
        double rateToday = 0.88d
        List<Point> realRatePoints = [
                new Point(1d, 0.85d),
                new Point(30d, rateToday)
        ]
        double rateTomorrow = 0.91d
        RatePercentCalculator calculator = new RatePercentCalculator(realRatePoints, rateTomorrow)

        when:
        String changePercent = calculator.calculatePercent()

        then:
        changePercent == "+3.41%"
    }

    def "should calculate percent based on rate today (0.91) and rate tomorrow (0.88)"() {
        given:
        double rateToday = 0.91d
        List<Point> realRatePoints = [
                new Point(1d, 0.85d),
                new Point(30d, rateToday)
        ]
        double rateTomorrow = 0.88d
        RatePercentCalculator calculator = new RatePercentCalculator(realRatePoints, rateTomorrow)

        when:
        String changePercent = calculator.calculatePercent()

        then:
        changePercent == "-3.30%"
    }

    def "should calculate percent based on rate today (0.91) and rate tomorrow (0.91)"() {
        given:
        double rateToday = 0.91d
        List<Point> realRatePoints = [
                new Point(1d, 0.85d),
                new Point(30d, rateToday)
        ]
        double rateTomorrow = 0.91d
        RatePercentCalculator calculator = new RatePercentCalculator(realRatePoints, rateTomorrow)

        when:
        String changePercent = calculator.calculatePercent()

        then:
        changePercent == "0.00%"
    }
}
