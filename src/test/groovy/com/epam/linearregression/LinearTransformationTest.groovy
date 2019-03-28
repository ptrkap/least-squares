package com.epam.linearregression

import spock.lang.Specification

class LinearTransformationTest extends Specification {

    def "for no points should throw exception"() {
        given:
        LinearTransformation linearTransformation = new LinearTransformation()
        List<Double> days = new ArrayList<>()
        Coefficients coefficients = new Coefficients(2d, 3d)

        when:
        linearTransformation.transform(days, coefficients)

        then:
        thrown(NotEnoughPointsException)
    }

    def "for one points should throw exception"() {
        given:
        LinearTransformation linearTransformation = new LinearTransformation()
        List<Double> days = new ArrayList<>()
        days.add(1)
        Coefficients coefficients = new Coefficients(2d, 3d)

        when:
        linearTransformation.transform(days, coefficients)

        then:
        thrown(NotEnoughPointsException)
    }

    def "for parameters a = 2 and b = 3  and  x = 1, x = 4 should return respectively y = 5, y = 11"() {
        given:
        LinearTransformation linearTransformation = new LinearTransformation()
        List<Double> days = [1d, 4d]
        Coefficients coefficients = new Coefficients(2d, 3d)

        when:
        List<Double> trans = linearTransformation.transform(days, coefficients)

        then:
        trans == [5d, 11d]
    }
}
