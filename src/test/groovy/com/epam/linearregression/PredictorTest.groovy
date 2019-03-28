package com.epam.linearregression

import spock.lang.Specification

class PredictorTest extends Specification {

    def "should predict rate one day ahead"() {
        given:
        Predictor predictor = new Predictor()
        Coefficients coefficients = new Coefficients(0.01d, 0.88d)
        double day = 31d

        when:
        double rateTomorrow = predictor.predict(coefficients, day)

        then:
        rateTomorrow == 1.19d
    }
}
