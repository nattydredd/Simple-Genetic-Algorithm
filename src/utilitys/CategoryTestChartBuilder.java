package utilitys;

// @author Nate
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class CategoryTestChartBuilder extends ApplicationFrame {

    //Variables
    private final String chartTitle;
    private final ArrayList inputData;
    final CategoryDataset dataset;
    private ChartPanel chartPanel;
    private JFreeChart chart;

    //Constructors   
    public CategoryTestChartBuilder(String chartTitle, ArrayList inputData) {
        super(chartTitle);

        this.chartTitle = chartTitle;
        this.inputData = inputData;
        this.dataset = createDataSet();
        this.chart = createChart(dataset);
        this.chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1024, 768));
        setContentPane(chartPanel);
    }

    //Methods
    private JFreeChart createChart(final CategoryDataset dataset) {

        //Create the chart...
        final JFreeChart chart = ChartFactory.createLineChart(
                "Mutation Rate Test", // chart title
                "Mutation Rate", // domain axis label
                "Average Fitness", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips
                false // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
//        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);
        //    legend.setShapeScaleX(1.5);
        //  legend.setShapeScaleY(1.5);
        //legend.setDisplaySeriesLines(true);
        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        //Customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

      
        //Customise the renderer...
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        renderer.setDrawShapes(true);

        renderer.setSeriesStroke(
                0, new BasicStroke(
                        2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                        1.0f, new float[]{10.0f, 6.0f}, 0.0f
                )
        );

        return chart;
    }

    private CategoryDataset createDataSet() {

        //Row keys
        final String series1 = "Best Fitness Average";
        //Create the dataset
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < inputData.size(); i++) {
            double[] row = (double[]) inputData.get(i);
            //Y Value, Variable, X Value
            dataset.addValue(row[1], series1, Double.toString(row[0]));
        }
        
        return dataset;
    }
}//End CategoryTestChartBuilder
