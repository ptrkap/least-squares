package com.epam;

import com.epam.linearregression.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        SetupInitializer propertiesReader = new SetupInitializer();
        Setup setup = propertiesReader.readProperties();
        System.out.println("--------------------------------------------");
        System.out.println("onlyRecentPrediction: " + setup.isOnlyRecentPrediction());
        System.out.println("interval.minutes: " + setup.getIntervalInMinutes());
        System.out.println("frame.days: " + setup.getFrameInDays());
        System.out.println("--------------------------------------------");

        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("dollar-euro.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        RealRatesContainer realRatesContainer = new RealRatesContainer();
        bufferedReader.lines().forEach(line -> {
            realRatesContainer.add(line.split(","));
        });
        List<Point> realRatesPoints = realRatesContainer.getPoints();
        PointsToXYSeriesConverter pointsToXYSeriesConverter = new PointsToXYSeriesConverter();
        XYSeries realRatesXY = pointsToXYSeriesConverter.convert(realRatesPoints, "Real rates");

        LeastSquares leastSquares = new LeastSquares();
        Coefficients coefficients = leastSquares.calculate(realRatesPoints);
        LinearTransformation linearTransformation = new LinearTransformation();
        PointsTransformations pointsTransformations = new PointsTransformations();
        List<Double> days = pointsTransformations.transformToDays(realRatesPoints);
        List<Double> regressionRates = linearTransformation.transform(days, coefficients);
        List<Point> regressionRatesPoints = pointsTransformations.transformToPoints(days, regressionRates);
        XYSeries regressionRatesXY = pointsToXYSeriesConverter.convert(regressionRatesPoints, "Regression rates");

        Predictor predictor = new Predictor();
        int predictedDay = 31;
        double rateTomorrow = predictor.predict(coefficients, predictedDay);
        XYSeries predictedRates = new XYSeries("Predicted rates");
        predictedRates.add(predictedDay, rateTomorrow);

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(predictedRates);
        xySeriesCollection.addSeries(realRatesXY);
        xySeriesCollection.addSeries(regressionRatesXY);

        String rate = "0.8832"; // tmp
        String percent = "+0.5%"; // tmp
        String prediction = String.format("USD/EURO tomorrow: %s [%s]", rate, percent);
        boolean growth = coefficients.getA() > 0;

        Chart chart = new Chart();
        chart.draw("Forex USD/EUR prediction", prediction, growth, xySeriesCollection);
    }
}
