package com.epam;

import com.epam.linearregression.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
        assert inputStream != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        RealRatesContainer realRatesContainer = new RealRatesContainer();
        bufferedReader.lines().forEach(line -> realRatesContainer.add(line.split(",")));
        List<Point> realRatesPoints = realRatesContainer.getPoints();
        PointsToXYSeriesConverter pointsToXYSeriesConverter = new PointsToXYSeriesConverter();
        XYSeries realRatesXY = pointsToXYSeriesConverter.convert(realRatesPoints, "Real rates");

        List<Point> regressionPoints = getRegressionPoints(realRatesPoints);
        XYSeries regressionRatesXY  = pointsToXYSeriesConverter.convert(regressionPoints,"Regression rates");

        Predictor predictor = new Predictor();
        int predictedDay = 31;
        LeastSquares leastSquares = new LeastSquares();
//        Coefficients coefficients = leastSquares.calculate(realRatesPoints.subList(600, 721)); //tmp
        Coefficients coefficients = leastSquares.calculate(realRatesPoints); //tmp
        double rateTomorrow = predictor.predict(coefficients, predictedDay);
        XYSeries predictedRates = new XYSeries("Predicted rates");
        predictedRates.add(predictedDay, rateTomorrow);

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(predictedRates);
        xySeriesCollection.addSeries(realRatesXY);
        xySeriesCollection.addSeries(regressionRatesXY);

        RatePercentCalculator ratePercentCalculator = new RatePercentCalculator(realRatesPoints, rateTomorrow);
        String percent = ratePercentCalculator.calculatePercent();
        String prediction = String.format("USD/EURO tomorrow: %s [%s]", String.format("%.4f", rateTomorrow), percent);
        boolean growth = coefficients.getA() > 0;

        Chart chart = new Chart();
        chart.draw("Forex USD/EUR prediction", prediction, growth, xySeriesCollection);
    }

    private static List<Point> getRegressionPoints(List<Point> realRatesPoints) {
        LeastSquares leastSquares = new LeastSquares();
        LinearTransformation linearTransformation = new LinearTransformation();
        PointsTransformations pointsTransformations = new PointsTransformations();
        List<Double> days = pointsTransformations.transformToDays(realRatesPoints);
//        Coefficients coefficients = leastSquares.calculate(realRatesPoints.subList(600, 721));

        Coefficients coefficients = leastSquares.calculate(realRatesPoints.subList(0, 100)); // tmp
        List<Double> regressionRates = linearTransformation.transform(days.subList(0, 100), coefficients);
        List<Point> regressionRatesPoints1 = pointsTransformations.transformToPoints(days.subList(0, 100), regressionRates);

        Coefficients coefficients2 = leastSquares.calculate(realRatesPoints.subList(100, 200)); // tmp
        List<Double> regressionRates2 = linearTransformation.transform(days.subList(100, 200), coefficients2);
        List<Point> regressionRatesPoints2 = pointsTransformations.transformToPoints(days.subList(100, 200), regressionRates2);

        List<Point> regressionRatesPoints = new ArrayList<>();
        regressionRatesPoints.addAll(regressionRatesPoints1);
        regressionRatesPoints.addAll(regressionRatesPoints2);

        return regressionRatesPoints;
    }
}
