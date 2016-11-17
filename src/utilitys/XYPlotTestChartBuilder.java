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

public class XYPlotTestChartBuilder extends ApplicationFrame {

    //Variables 
    private final String chartTitle;
    private final ArrayList inputData;
    private XYSeriesCollection aCollection;
    private XYSeriesCollection bCollection;
    private ChartPanel chartPanel;
    private JFreeChart lineChart;

    //Constructors   
    public XYPlotTestChartBuilder(String chartTitle, ArrayList inputData) {
        super(chartTitle);

        this.chartTitle = chartTitle;
        this.inputData = inputData;
        this.aCollection = new XYSeriesCollection();
        this.bCollection = new XYSeriesCollection();
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
        plot.setDataset(0, aCollection);
        plot.setDataset(1, bCollection);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        //Customise renderers
        final XYSplineRenderer aRenderer = new XYSplineRenderer();
        aRenderer.setSeriesPaint(0, new Color(0x00, 0x33, 0xcc));
        plot.setRenderer(0, aRenderer);

        final XYSplineRenderer bRenderer = new XYSplineRenderer();
        bRenderer.setSeriesPaint(0, new Color(0x00, 0xe6, 0x00));
        plot.setRenderer(1, bRenderer);

        //Customise y axis
        final NumberAxis yAxis = new NumberAxis("Average Fitness");
//        yAxis.setRange(10, 30);
        yAxis.setTickUnit(new NumberTickUnit(1));
        plot.setRangeAxis(yAxis);

        //Customise x axis    
        final NumberAxis xAxis = new NumberAxis("Mutation Rate");
//        xAxis.setRange(0.005, 0.025);
        xAxis.setTickUnit(new NumberTickUnit(0.001));
        plot.setDomainAxis(xAxis);

        //Map data to appropriate axis
        plot.mapDatasetToDomainAxis(0, 0);
        plot.mapDatasetToDomainAxis(1, 0);

        return lineChart = new JFreeChart(chartTitle, getFont(), plot, rootPaneCheckingEnabled);
    }

    private void createDataSet() {

        XYSeries aSeries = new XYSeries("Average Population Fitness");
        XYSeries bSeries = new XYSeries("Average Fittest Individual");

        double[] row;

        for (int i = 0; i < inputData.size(); i++) {
            row = (double[]) inputData.get(i);
            aSeries.add(i, row[1]);
            bSeries.add(i, row[2]);
        }

        this.aCollection.addSeries(aSeries);
        this.bCollection.addSeries(bSeries);
    }
    
}//End ChartBuilder
