package utilitys;

// @author Nate
import java.awt.Dimension;
import java.util.ArrayList;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class ChartBuilder extends ApplicationFrame {

    //Variables
    ChartPanel chartPanel;
    private final String chartTitle;
    private final JFreeChart lineChart;
    private final ArrayList inputData;
    private DefaultCategoryDataset data;

    //Constructors   
    public ChartBuilder(String chartTitle, ArrayList inputData) {
        super(chartTitle);
        
        this.chartTitle = chartTitle;
        this.inputData = inputData;
        this.lineChart = createChart();
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(1024, 768));
        setContentPane(chartPanel);
    }

    //Methods
    public JFreeChart createChart() {
        JFreeChart chart = ChartFactory.createLineChart(
                chartTitle, // chart title
                "Generation", // domain axis label
                "Fitness", // range axis label
                createDataSet(), // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips
                false // urls
        );

        return chart;
    }

    private DefaultCategoryDataset createDataSet() {

        //Rows
        String averageFitness = "Average Fitness";
        String best = "Best Fitness";
        //Column
        String generation;
        //Data
        data = new DefaultCategoryDataset();
        double[] row;

        for (int i = 0; i < inputData.size(); i++) {
            generation = "" + i;
            row = (double[]) inputData.get(i);
            data.addValue(row[0], averageFitness, generation);
            data.addValue(row[1], best, generation);
        }

        return data;
    }
}//End ChartBuilder
