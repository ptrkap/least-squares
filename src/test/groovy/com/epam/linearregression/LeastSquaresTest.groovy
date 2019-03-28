package com.epam.linearregression

import spock.lang.Specification

class LeastSquaresTest extends Specification {

    def "should throw exception when there is no points"() {
        given:
        LeastSquares leastSquares = new LeastSquares()
        List<Point> points = new ArrayList<>()

        when:
        leastSquares.calculate(points)

        then:
        thrown(NotEnoughPointsException)
    }

    def "should throw exception when there is only one point"() {
        given:
        LeastSquares leastSquares = new LeastSquares()
        List<Point> points = new ArrayList<>()
        points.add(new Point(0.88, 4))

        when:
        leastSquares.calculate(points)

        then:
        thrown(NotEnoughPointsException)
    }

}
