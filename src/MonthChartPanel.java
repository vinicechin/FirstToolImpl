import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import java.text.DateFormatSymbols;
import java.util.List;

/**
 * Created by Vinicius on 24/07/2016.
 */
public class MonthChartPanel extends ChartPanel {

    private DefaultPieDataset pieData;
    private List<ImageInfo> imageList;
    private enum meses { janeiro, fevereiro, março, abril, maio, junho, julho, agosto, setembro, outubro, novembro, dezembro}

    /********************************************************************************************************/
    /** Constructor */
    public MonthChartPanel(JFreeChart chart) {
        super(chart);

        this.imageList = null;
    }

    public void setImageList(List<ImageInfo> imageList) {
        this.imageList = imageList;
    }

    /********************************************************************************************************/
    /** Methods */
    public void updatePieData(){
        JFreeChart newChart;

        this.pieData = new DefaultPieDataset();
        if(this.imageList != null) {
            for (int i = 1; i <= 12; i++) {
                int count = 0;
                for (ImageInfo j : this.imageList) {
                    if (i == j.getMonth()) {
                        count++;
                    }
                }
                if (count > 0)
                    this.pieData.setValue(new DateFormatSymbols().getMonths()[i - 1], count);
            }
        }

        newChart = ChartFactory.createPieChart3D("Month Distribution", this.pieData, true, true, false );

        final PiePlot3D plot = ( PiePlot3D ) newChart.getPlot( );
        plot.setForegroundAlpha( 0.70f );
        plot.setInteriorGap( 0.02 );

        this.setChart(newChart);

        this.repaint();
    }
}
