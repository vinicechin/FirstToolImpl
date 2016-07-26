import org.jfree.chart.ChartFactory;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Vinicius on 12/04/2016.
 */
public class MainPanel extends JPanel {

    private MonthChartPanel  monthsPieChartPanel;
    private ScatterPlotPanel scatterTestPanel;
    private List<ImageInfo>  imageList;
    private int              selected;
    private String           xAxis;
    private String           yAxis;
    private JComboBox        vertexSelectorX;
    private JComboBox        vertexSelectorY;
    private ImageInfo        selectedImage;

    /********************************************************************************************************/
    /** Contructor */
    public MainPanel(){
        super();
        this.selected = 1;

        //red: 255 double, green: 255 double, blue: 255 double, hue: 360 double, sat: 100 double, bri: 100 double, year: n double(int), month: 12 int, height: m double, width: i double, total size: m*i double
        this.vertexSelectorX  = new javax.swing.JComboBox<>();
        this.vertexSelectorX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue", "Hue", "Saturation", "Brightness", "Year", "Month", "Full Date", "Height", "Width", "Area" }));
        this.vertexSelectorX.setSelectedIndex(0);
        this.add(this.vertexSelectorX);
        this.vertexSelectorX.addItemListener(evt -> VertexSelectorXItemStateChanged(evt));

        this.vertexSelectorY  = new javax.swing.JComboBox<>();
        this.vertexSelectorY.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue", "Hue", "Saturation", "Brightness", "Year", "Month", "Full Date", "Height", "Width", "Area" }));
        this.vertexSelectorY.setSelectedIndex(1);
        this.add(this.vertexSelectorY);
        this.vertexSelectorY.addItemListener(evt -> VertexSelectorYItemStateChanged(evt));
        this.xAxis = "Red";
        this.yAxis = "Green";
    }

    /***************************************************************************************************/
    /** Events */
    private void VertexSelectorXItemStateChanged(java.awt.event.ItemEvent evt) {
        if(evt.getStateChange() == 1){
            this.xAxis = evt.getItem().toString();
            this.repaint();
        }
    }

    private void VertexSelectorYItemStateChanged(java.awt.event.ItemEvent evt) {
        if(evt.getStateChange() == 1){
            this.yAxis = evt.getItem().toString();
            this.repaint();
        }
    }

    /********************************************************************************************************/
    /** Getters */
    public MonthChartPanel getMonthsPieChartPanel() {
        return monthsPieChartPanel;
    }

    public List<ImageInfo> getImageList() {
        return imageList;
    }

    /********************************************************************************************************/
    /** Setters */
    public void setMonthsPieChartPanel(MonthChartPanel monthsPieChartPanel) {
        this.monthsPieChartPanel = monthsPieChartPanel;
    }

    public void setImageList(List<ImageInfo> imageList) {
        this.imageList = imageList;
    }

    /********************************************************************************************************/
    /** Methods */

    @Override
    public void paintComponent(Graphics g){
        //inicializations
        super.paintComponent(g);

        //boxes
        this.vertexSelectorX.setBounds((int) (this.getWidth()/2 - this.vertexSelectorX.getSize().getWidth()), 6, 70, 18);
        this.vertexSelectorY.setBounds(this.getWidth()/2 + 5, 6, 70, 18);

        if(this.imageList != null) {
            // month pie chart
            if(selected == 0) {
                if (this.monthsPieChartPanel == null) {
                    this.monthsPieChartPanel = new MonthChartPanel(ChartFactory.createPieChart("Chart", new DefaultPieDataset(), true, true, false), imageList);
                    this.add(this.monthsPieChartPanel);
                }
                this.monthsPieChartPanel.setBounds(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                this.monthsPieChartPanel.updatePieData();
            }
            if(selected == 1) {
                //scatter chart
                if (this.scatterTestPanel == null) {
                    this.scatterTestPanel = new ScatterPlotPanel(ChartFactory.createPieChart("Chart", new DefaultPieDataset(), true, true, false), imageList);
                    this.add(this.scatterTestPanel);
                }
                this.scatterTestPanel.setBounds(5, 25, this.getWidth() - 10, this.getHeight() - 30);
                this.scatterTestPanel.UpdateScatterDataset(this.xAxis, this.yAxis, this.selectedImage);
            }
            // other chart

        }
    }

    public void selectImage(ImageInfo image){
        this.selectedImage = image;
        this.repaint();
    }

}
