package com.epam;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LeastSquaresLinearRegression {

    public static void main(String[] args) throws IOException {
        SetupInitializer propertiesReader = new SetupInitializer();
        Setup setup = propertiesReader.readProperties();
        System.out.println("--------------------------------------------");
        System.out.println("onlyRecentPrediction: " + setup.isOnlyRecentPrediction());
        System.out.println("interval.minutes: " + setup.getIntervalInMinutes());
        System.out.println("frame.days: " + setup.getFrameInDays());
        System.out.println("--------------------------------------------");

        LeastSquaresLinearRegression regression = new LeastSquaresLinearRegression();

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

        regression.printChart("Forex USD/EUR prediction", prediction, growth, xySeriesCollection);
    }

    void printChart(String title, String prediction, boolean growth, XYSeriesCollection xySeriesCollection) {
        JFreeChart chart = ChartFactory.createScatterPlot(
                " ",
                "Time [days]",
                "USD/EUR",
                xySeriesCollection,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );
        JPanel panel = new ChartPanel(chart);
        JLabel predictionLabel = new JLabel(prediction);
        predictionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        Color color = growth ? new Color(0, 190, 0) : new Color(230, 0,0);
        predictionLabel.setForeground(color);
        panel.add(predictionLabel);
        JFrame frame = new JFrame();
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle(title);
        frame.setVisible(true);
    }
}
