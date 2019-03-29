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

        RegressionData regressionData = getRegressionData(realRatesPoints, setup);
        XYSeries regressionRatesXY = new XYSeries("Regression rates");
        pointsToXYSeriesConverter.convert(regressionData.getRegressionPoints(), regressionRatesXY);

        int lastPredicted = regressionData.getPredictedPoints().size() - 1;
        double rateTomorrow = regressionData.getPredictedPoints().get(lastPredicted).getDollarEuroRate();

        XYSeries predictedRatesXY = new XYSeries("Predicted rates");
        pointsToXYSeriesConverter.convert(regressionData.getPredictedPoints(), predictedRatesXY);

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(predictedRatesXY);
        xySeriesCollection.addSeries(realRatesXY);
        xySeriesCollection.addSeries(regressionRatesXY);

        RatePercentCalculator ratePercentCalculator = new RatePercentCalculator(realRatesPoints, rateTomorrow);
        String percent = ratePercentCalculator.calculatePercent();
        String prediction = String.format(
                "USD/EURO tomorrow: %s [%s]",
                String.format("%.4f", rateTomorrow).replace(",","."),
                percent);
        boolean growth = ratePercentCalculator.isGrowth();
        Chart chart = new Chart();
        chart.draw("Forex USD/EUR prediction", prediction, growth, xySeriesCollection);
    }

    private static RegressionData getRegressionData(List<Point> realRatesPoints, Setup setup) {
        PointsTransformations pointsTransformations = new PointsTransformations();
        List<Double> days = pointsTransformations.transformToDays(realRatesPoints);
        SamplesCalculator samplesCalculator = new SamplesCalculator(setup);
        int samplesNumber =  samplesCalculator.calculateSamplesNumberPerRegression();
        int lastStartPoint = samplesCalculator.calculateLastStartPoint(days, samplesNumber);
        List<Point> regressionRatesPoints = new ArrayList<>();
        List<Point> predictedPointsPerIteration = new ArrayList<>();
        RegressionData regressionData = new RegressionData(regressionRatesPoints, predictedPointsPerIteration);
        for (int i = 0; i <= lastStartPoint; i++) {
            RegressionData regressionDataOfIteration = createRegressionDataPerIteration(
                    realRatesPoints,
                    pointsTransformations,
                    days,
                    i,
                    i + samplesNumber);
            regressionRatesPoints.addAll(regressionDataOfIteration.getRegressionPoints());
            predictedPointsPerIteration.addAll(regressionDataOfIteration.getPredictedPoints());
            if (i == lastStartPoint) {
                regressionData.setCoefficients(regressionDataOfIteration.getCoefficients());
            }
        }
        return regressionData;
    }

    private static RegressionData createRegressionDataPerIteration(
            List<Point> realRatesPoints,
            PointsTransformations pointsTransformations,
            List<Double> days,
            int from,
            int to) {
        LinearTransformation linearTransformation = new LinearTransformation();
        LeastSquares leastSquares = new LeastSquares();
        Coefficients coefficients = leastSquares.calculate(realRatesPoints.subList(from, to));
        List<Double> regressionRates = linearTransformation.transform(days.subList(from, to), coefficients);
        List<Point> regressionPoints = pointsTransformations.transformToPoints(days.subList(from, to), regressionRates);
        List<Point> predictedPoints = new ArrayList<>();
        Predictor predictor = new Predictor();
        double predictedDay = days.get(to) + 1;
        double predictedRate = predictor.predict(coefficients, predictedDay);
        predictedPoints.add(new Point(predictedDay, predictedRate));
        RegressionData regressionData = new RegressionData(
                regressionPoints,
                predictedPoints);
        regressionData.setCoefficients(coefficients);
        return regressionData;
    }
}
