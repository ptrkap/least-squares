package com.epam.linearregression

import org.jfree.data.xy.XYSeries
import spock.lang.Specification

class PointsToXYSeriesConverterTest extends Specification {

    def "should return empty XYSeries"() {
        given:
        PointsToXYSeriesConverter converter = new PointsToXYSeriesConverter()
        List<Point> points = new ArrayList<>()

        when:
        XYSeries xySeries = new XYSeries("Predicted rates")
        converter.convert(points, xySeries)

        then:
        xySeries.getItemCount() == 0
    }

    def "should return one element long XYSeries"() {
        given:
        PointsToXYSeriesConverter converter = new PointsToXYSeriesConverter()
        List<Point> points = new ArrayList<>()
        points.add(new Point(1, 0.88))

        when:
        XYSeries xySeries = new XYSeries("Predicted rates")
        converter.convert(points, xySeries)

        then:
        xySeries.getItemCount() == 1
    }
}
