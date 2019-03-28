package com.epam.linearregression

import spock.lang.Specification

class PointsTransformationsTest extends Specification {

    def "should transform empty real rates points list to empty days list"() {
        given:
        PointsTransformations pointsTransformations = new PointsTransformations()
        List<Point> realRatesPoints = []

        when:
        List<Double> days = pointsTransformations.transformToDays(realRatesPoints)

        then:
        days.size() == 0
    }

    def "should transform real rates points list to days list"() {
        given:
        PointsTransformations pointsTransformations = new PointsTransformations()
        List<Point> realRatesPoints = [
                new Point(1.0d, 0.88d),
                new Point(2.0d, 0.87d)
        ]

        when:
        List<Double> days = pointsTransformations.transformToDays(realRatesPoints)

        then:
        days == [1.0d, 2.0d]
    }

    def "should transform empty days list and empty dollarEuroRate list to empty regression points list"() {
        given:
        PointsTransformations pointsTransformations = new PointsTransformations()
        List<Double> days = []
        List<Double> dollarEuroRates = []

        when:
        List<Point> regressionPoints = pointsTransformations.transformToPoints(days, dollarEuroRates)

        then:
        regressionPoints.size() == 0
    }

    def "should transform days list and dollarEuroRate list to regression points list"() {
        given:
        PointsTransformations pointsTransformations = new PointsTransformations()
        List<Double> days = [1.0d, 2.0d]
        List<Double> dollarEuroRates = [0.88d, 0.87d]

        when:
        List<Point> regressionPoints = pointsTransformations.transformToPoints(days, dollarEuroRates)

        then:
        regressionPoints == [
                new Point(1d, 0.88d),
                new Point(2d, 0.87d)
        ]
    }
}
