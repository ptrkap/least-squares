package com.epam.linearregression;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

import javax.swing.*;
import java.awt.*;

public class Chart {

    public void draw(String title, String prediction, boolean growth, XYSeriesCollection xySeriesCollection) {
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
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.getRenderer().setSeriesShape(0, ShapeUtilities.createRegularCross(1, 1));
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
