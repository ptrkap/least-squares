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
        XYSeries realRatesXY = new XYSeries("Real rates");
        pointsToXYSeriesConverter.convert(realRatesPoints, realRatesXY);

        List<Point> regressionPoints = getRegressionPoints(realRatesPoints, setup);
        XYSeries regressionRatesXY = new XYSeries("Regression rates");
        pointsToXYSeriesConverter.convert(regressionPoints, regressionRatesXY);

        Predictor predictor = new Predictor();
        int predictedDay = 31;
        LeastSquares leastSquares = new LeastSquares();
//        Coefficients coefficients = leastSquares.calculate(realRatesPoints.subList(600, 721)); //tmp
        Coefficients coefficients = leastSquares.calculate(realRatesPoints); //tmp
        double rateTomorrow = predictor.predict(coefficients, predictedDay);
        XYSeries predictedRatesXY = new XYSeries("Predicted rates");
        predictedRatesXY.add(predictedDay, rateTomorrow);


        List<Point> predictedPoints = new ArrayList<>();
        predictedPoints.add(new Point(30, 1));
        pointsToXYSeriesConverter.convert(predictedPoints, predictedRatesXY);


        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(predictedRatesXY);
        xySeriesCollection.addSeries(realRatesXY);
        xySeriesCollection.addSeries(regressionRatesXY);

        RatePercentCalculator ratePercentCalculator = new RatePercentCalculator(realRatesPoints, rateTomorrow);
        String percent = ratePercentCalculator.calculatePercent();
        String prediction = String.format("USD/EURO tomorrow: %s [%s]", String.format("%.4f", rateTomorrow), percent);
        boolean growth = coefficients.getA() > 0;

        Chart chart = new Chart();
        chart.draw("Forex USD/EUR prediction", prediction, growth, xySeriesCollection);
    }

    private static List<Point> getRegressionPoints(List<Point> realRatesPoints, Setup setup) {
        PointsTransformations pointsTransformations = new PointsTransformations();
        List<Double> days = pointsTransformations.transformToDays(realRatesPoints);
        SamplesCalculator samplesCalculator = new SamplesCalculator(setup);
        int samplesNumber =  samplesCalculator.calculateSamplesNumberPerRegression();
        int lastStartPoint = samplesCalculator.calculateLastStartPoint(days, samplesNumber);
        List<Point> regressionRatesPoints = new ArrayList<>();
        for (int i = 0; i <= lastStartPoint; i++) {
            List<Point> regressionRatesPoints1 = createRegressionPoints(
                    realRatesPoints,
                    pointsTransformations,
                    days,
                    i,
                    i + samplesNumber);
            regressionRatesPoints.addAll(regressionRatesPoints1);
        }
        return regressionRatesPoints;
    }

    private static List<Point> createRegressionPoints(
            List<Point> realRatesPoints,
            PointsTransformations pointsTransformations,
            List<Double> days,
            int from,
            int to) {
        LinearTransformation linearTransformation = new LinearTransformation();
        LeastSquares leastSquares = new LeastSquares();
        Coefficients coefficients = leastSquares.calculate(realRatesPoints.subList(from, to));
        List<Double> regressionRates = linearTransformation.transform(days.subList(from, to), coefficients);
        return pointsTransformations.transformToPoints(days.subList(from, to), regressionRates);
    }
}
