package com.epam.linearregression;

import org.jfree.data.xy.XYSeries;
import java.util.List;

public class PointsToXYSeriesConverter {

    public XYSeries convert(List<Point> points, String name) {
        XYSeries xySeries = new XYSeries(name);
        for (Point point : points) {
            xySeries.add(point.getDay(), point.getDollarEuroRate());
        }
        return xySeries;
    }
}
