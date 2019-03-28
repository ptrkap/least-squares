package com.epam.linearregression

import spock.lang.Specification

class RealRatesContainerTest extends Specification {

    def "for no real rates should return empty list"() {
        given:
        RealRatesContainer realRatesContainer = new RealRatesContainer();

        when:
        List<Point> points = realRatesContainer.getPoints()

        then:
        points.size() == 0;
    }

    def "for [0, 0.88] and [1, 0.87] should return two element long list"() {
        given:
        RealRatesContainer realRatesContainer = new RealRatesContainer();
        realRatesContainer.add((String [])["0", "0.88"])
        realRatesContainer.add((String [])["1", "0.87"])

        when:
        List<Point> points = realRatesContainer.getPoints()

        then:
        points.size() == 2;
    }
}
