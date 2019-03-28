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

    def "for points (1, 5) and (4, 11) should return a = 2 and b = 3"() {
        given:
        LeastSquares leastSquares = new LeastSquares()
        List<Point> points = new ArrayList<>()
        points.add(new Point(1, 5))
        points.add(new Point(4, 11))

        when:
        Coefficients coefficients = leastSquares.calculate(points)

        then:
        coefficients.a == 2
        coefficients.b == 3
    }
}
