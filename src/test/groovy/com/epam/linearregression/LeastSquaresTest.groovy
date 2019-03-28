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

    def "for points (1, 2), (2, 4), (3, 5), (4, 4), (5, 5) should return a = 0.6 and b = 2.2"() {
        given:
        LeastSquares leastSquares = new LeastSquares()
        List<Point> points = new ArrayList<>()
        points.add(new Point(1, 2))
        points.add(new Point(2, 4))
        points.add(new Point(3, 5))
        points.add(new Point(4, 4))
        points.add(new Point(5, 5))

        when:
        Coefficients coefficients = leastSquares.calculate(points)

        then:
        coefficients.a == 0.6
        coefficients.b == 2.2
    }
}
