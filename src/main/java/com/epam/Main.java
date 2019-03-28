package com.epam;

import com.epam.linearregression.Chart;
import com.epam.linearregression.Point;
import com.epam.linearregression.RealRatesContainer;
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
        List<Point> points = realRatesContainer.getPoints();

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();

        XYSeries realRates = new XYSeries("Real rates");
        realRates.add(2.5, 3.5);
        realRates.add(4.5, 5.5);

        XYSeries regressionRates = new XYSeries("Regression rates");
        regressionRates.add(4.5, 5.5);
        regressionRates.add(6.5, 7.5);

        XYSeries predictedRates = new XYSeries("Predicted rates");
        predictedRates.add(7.5, 8.5);
        predictedRates.add(9.5, 10.5);

        xySeriesCollection.addSeries(realRates);
        xySeriesCollection.addSeries(regressionRates);
        xySeriesCollection.addSeries(predictedRates);

        String rate = "0.8832";
        String percent = "+0.5%";
        String prediction = String.format("USD/EURO tomorrow: %s [%s]", rate, percent);
        boolean growth = true; // tmp

        Chart chart = new Chart();
        chart.draw("Forex USD/EUR prediction", prediction, growth, xySeriesCollection);
    }
}
