import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Vinicius on 24/07/2016.
 */
public class ScatterPlotPanel extends ChartPanel{

    private XYSeriesCollection scatterDataset;
    private List<ImageInfo>  imageList;


    /********************************************************************************************************/
    /** Contructor */
    public ScatterPlotPanel(JFreeChart chart) {
        super(chart);

        this.imageList = null;
    }

    public void setImageList(List<ImageInfo> imageList) {
        this.imageList = imageList;
    }

    /********************************************************************************************************/
    /** Methods */

    public void UpdateScatterDataset(String xAxis, String yAxis, ImageInfo selectedImage){
        JFreeChart newChart;
        XYSeries selectedSeries = null;
        XYSeries series1 = new XYSeries("Van Gogh");
        double xvalue = 0, yvalue = 0;

        this.scatterDataset = new XYSeriesCollection();

        if(this.imageList != null) {
            for (ImageInfo i : this.imageList) {
                switch (xAxis) {
                    case "Red":
                        xvalue = i.getRedValue();
                        break;
                    case "Green":
                        xvalue = i.getGreenValue();
                        break;
                    case "Blue":
                        xvalue = i.getBlueValue();
                        break;
                    case "Hue":
                        xvalue = i.getHueValue();
                        break;
                    case "Saturation":
                        xvalue = i.getSatValue();
                        break;
                    case "Brightness":
                        xvalue = i.getLumValue();
                        break;
                    case "Month":
                        xvalue = i.getMonth();
                        break;

                    case "Year":
                        xvalue = Math.round(i.getYear());
                        break;
                    case "Height":
                        xvalue = i.getHeight();
                        break;
                    case "Width":
                        xvalue = i.getWidth();
                        break;
                    case "Area":
                        xvalue = i.getArea();
                        break;

                    case "Full Date":
                        xvalue = (Math.round(i.getYear()) + ((i.getMonth() - 0.5) / 12.0));
                        break;
                }
                switch (yAxis) {
                    case "Red":
                        yvalue = i.getRedValue();
                        break;
                    case "Green":
                        yvalue = i.getGreenValue();
                        break;
                    case "Blue":
                        yvalue = i.getBlueValue();
                        break;
                    case "Hue":
                        yvalue = i.getHueValue();
                        break;
                    case "Saturation":
                        yvalue = i.getSatValue();
                        break;
                    case "Brightness":
                        yvalue = i.getLumValue();
                        break;
                    case "Month":
                        yvalue = i.getMonth();
                        break;

                    case "Year":
                        yvalue = Math.round(i.getYear());
                        break;
                    case "Height":
                        yvalue = i.getHeight();
                        break;
                    case "Width":
                        yvalue = i.getWidth();
                        break;
                    case "Area":
                        yvalue = i.getArea();
                        break;

                    case "Full Date":
                        xvalue = (Math.round(i.getYear()) + ((i.getMonth() - 0.5) / 12.0));
                }

                if (i == selectedImage) {
                    selectedSeries = new XYSeries("Selected");
                    selectedSeries.add(xvalue, yvalue);
                } else {
                    series1.add(xvalue, yvalue);
                }
            }
        }

        this.scatterDataset.addSeries(series1);
        if(selectedSeries != null){
            this.scatterDataset.addSeries(selectedSeries);
        }

        newChart = ChartFactory.createScatterPlot("Data Charts", xAxis, yAxis, this.scatterDataset, PlotOrientation.VERTICAL, true, true, false );

        this.setChart(newChart);

        this.repaint();
    }
}
