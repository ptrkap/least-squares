package com.epam.linearregression

import com.epam.Setup
import spock.lang.Specification

class SamplesCalculatorTest extends Specification {

    def "should calculate samples number per regression for update which is every hour and frame is 3 days"() {
        given:
        Properties properties = new Properties()
        properties.setProperty("interval.minutes", "60")
        properties.setProperty("frame.days", "3")
        Setup setup = new Setup(properties)
        SamplesCalculator samplesCalculator = new SamplesCalculator(setup)

        when:
        int samplesPerRegression = samplesCalculator.calculateSamplesNumberPerRegression()

        then:
        samplesPerRegression == 72
    }

    def "should calculate samples number per regression for update which is every 5 minutes and frame is 3 days"() {
        given:
        Properties properties = new Properties()
        properties.setProperty("interval.minutes", "5")
        properties.setProperty("frame.days", "3")
        Setup setup = new Setup(properties)
        SamplesCalculator samplesCalculator = new SamplesCalculator(setup)

        when:
        int samplesPerRegression = samplesCalculator.calculateSamplesNumberPerRegression()

        then:
        samplesPerRegression == 864
    }

    def "should calculate samples number per regression for update which is every 5 minutes and frame is 5 days"() {
        given:
        Properties properties = new Properties()
        properties.setProperty("interval.minutes", "5")
        properties.setProperty("frame.days", "5")
        Setup setup = new Setup(properties)
        SamplesCalculator samplesCalculator = new SamplesCalculator(setup)

        when:
        int samplesPerRegression = samplesCalculator.calculateSamplesNumberPerRegression()

        then:
        samplesPerRegression == 1440
    }

    def "should calculatate last start point"() {
        given:
        List<Double> days = [1d, 1.5d, 2d, 2.5d, 3d, 3.5d, 4d, 4.5d]
        Properties properties = new Properties()
        properties.setProperty("interval.minutes", "720")
        properties.setProperty("frame.days", "2")
        Setup setup = new Setup(properties)
        SamplesCalculator samplesCalculator = new SamplesCalculator(setup)

        when:
        int samplesNumberPerRegression = samplesCalculator.calculateSamplesNumberPerRegression()
        int lastStartPoint = samplesCalculator.calculateLastStartPoint(days, samplesNumberPerRegression)

        then:
        lastStartPoint == 3
    }
}
