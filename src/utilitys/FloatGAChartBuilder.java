package utilitys;

// @author Nate
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class FloatGAChartBuilder extends ApplicationFrame {

    //Variables 
    private final String chartTitle;
    private final ArrayList inputData;
    private XYSeriesCollection averageFitness;
    private XYSeriesCollection fittest;
    private XYSeriesCollection validationFitness;
    private ChartPanel chartPanel;
    private JFreeChart lineChart;

    //Constructors   
    public FloatGAChartBuilder(String chartTitle, ArrayList inputData) {
        super(chartTitle);

        this.chartTitle = chartTitle;
        this.inputData = inputData;
        this.averageFitness = new XYSeriesCollection();
        this.fittest = new XYSeriesCollection();
        this.validationFitness = new XYSeriesCollection();
        this.lineChart = createChart();
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(1024, 768));
        setContentPane(chartPanel);
    }

    //Methods
    public JFreeChart createChart() {

//Create datasets
        createDataSet();

        //Construct plot
        final XYPlot plot = new XYPlot();
        plot.setDataset(0, averageFitness);
        plot.setDataset(1, fittest);
        plot.setDataset(2, validationFitness);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        //Customise renderers
        final XYSplineRenderer avFitnessRenderer = new XYSplineRenderer();
        avFitnessRenderer.setSeriesPaint(0, new Color(0x00, 0x33, 0xcc));
        plot.setRenderer(0, avFitnessRenderer);

        final XYSplineRenderer fittestRenderer = new XYSplineRenderer();
        fittestRenderer.setSeriesPaint(0, new Color(0x00, 0xe6, 0x00));
        plot.setRenderer(1, fittestRenderer);
        
        final XYSplineRenderer validationRenderer = new XYSplineRenderer();
        validationRenderer.setSeriesPaint(0, new Color(0xff, 0x19, 0x00));
        plot.setRenderer(2, validationRenderer);

        //Customise y axis
        final NumberAxis yAxis = new NumberAxis("Fitness");
        yAxis.setTickUnit(new NumberTickUnit(50));
        plot.setRangeAxis(yAxis);

        //Customise x axis    
        final NumberAxis xAxis = new NumberAxis("Generations");
        xAxis.setTickUnit(new NumberTickUnit(200));
        plot.setDomainAxis(xAxis);

        //Map data to appropriate axis
        plot.mapDatasetToDomainAxis(0, 0);
        plot.mapDatasetToDomainAxis(1, 0);
        plot.mapDatasetToDomainAxis(2, 0);

        return lineChart = new JFreeChart(chartTitle, getFont(), plot, rootPaneCheckingEnabled);
    }

    private void createDataSet() {

        XYSeries averageFitnessSeries = new XYSeries("Average Fitness");
        XYSeries fittestSeries = new XYSeries("Fittest");
        XYSeries validationSeries = new XYSeries("Validation Fitness");

        double[] row;

        for (int i = 0; i < inputData.size(); i++) {
            row = (double[]) inputData.get(i);
            averageFitnessSeries.add(i, row[0]);
            fittestSeries.add(i, row[1]);
            validationSeries.add(i, row[2]);
        }

        this.averageFitness.addSeries(averageFitnessSeries);
        this.fittest.addSeries(fittestSeries);
        this.validationFitness.addSeries(validationSeries);
    }
}//End BinaryGAChartBuilder
