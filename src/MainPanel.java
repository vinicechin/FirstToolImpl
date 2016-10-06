import org.jfree.chart.ChartFactory;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinicius on 12/04/2016.
 */
public class MainPanel extends JPanel {

    private MonthChartPanel    monthsPieChartPanel;
    private ScatterPlotPanel   scatterTestPanel;
    private ParallelCoordPanel parallelCoordPanel;
    private List<ImageInfo>    imageList;
    private List<ImageInfo>    imageListToSend;
    private int                selected;
    private String             xAxis;
    private String             yAxis;
    private JComboBox          vertexSelectorX;
    private JComboBox          vertexSelectorY;
    private ImageInfo          selectedImage;
    private String             categoryType;

    /********************************************************************************************************/
    /** Contructor */
    public MainPanel(){
        super();
        this.selected = 2;

        this.imageList = null;
        this.imageListToSend = null;
        this.parallelCoordPanel = null;
        this.monthsPieChartPanel = null;
        this.scatterTestPanel = null;
        this.categoryType = "all";

        //red: 255 double, green: 255 double, blue: 255 double, hue: 360 double, sat: 100 double, bri: 100 double, year: n double(int), month: 12 int, height: m double, width: i double, total size: m*i double
        this.vertexSelectorX  = new javax.swing.JComboBox<>();
        this.vertexSelectorX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue", "Hue", "Saturation", "Brightness", "Year", "Month", "Full Date", "Height", "Width", "Area" }));
        this.vertexSelectorX.setSelectedIndex(0);
        this.add(this.vertexSelectorX);
        this.vertexSelectorX.addItemListener(evt -> VertexSelectorXItemStateChanged(evt));
        this.vertexSelectorX.setVisible(false);

        this.vertexSelectorY  = new javax.swing.JComboBox<>();
        this.vertexSelectorY.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue", "Hue", "Saturation", "Brightness", "Year", "Month", "Full Date", "Height", "Width", "Area" }));
        this.vertexSelectorY.setSelectedIndex(1);
        this.add(this.vertexSelectorY);
        this.vertexSelectorY.addItemListener(evt -> VertexSelectorYItemStateChanged(evt));
        this.vertexSelectorY.setVisible(false);

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

    public ScatterPlotPanel getScatterTestPanel() {
        return scatterTestPanel;
    }

    public ParallelCoordPanel getParallelCoordPanel() {
        return parallelCoordPanel;
    }

    public List<ImageInfo> getImageList() {
        return imageList;
    }

    public String getCategoryType() {
        return categoryType;
    }

    /********************************************************************************************************/
    /** Setters */
    public void setMonthsPieChartPanel(MonthChartPanel monthsPieChartPanel) {
        this.monthsPieChartPanel = monthsPieChartPanel;
    }

    public void setScatterTestPanel(ScatterPlotPanel scatterTestPanel) {
        this.scatterTestPanel = scatterTestPanel;
    }

    public void setParallelCoordPanel(ParallelCoordPanel parallelCoordPanel) {
        this.parallelCoordPanel = parallelCoordPanel;
    }

    public void setImageList(List<ImageInfo> imageList) {
        this.imageList = imageList;
    }

    public void setImageIntoList(ImageInfo image) {
        if(imageList == null){
            imageList = new ArrayList<>();
        }
        this.imageList.add(image);
    }

    public void SetImagesIntoSendList(List<ImageInfo> imageList){
        if(this.imageListToSend == null){
            this.imageListToSend = new ArrayList<>();
        }
        this.imageListToSend.clear();
        for(ImageInfo i : imageList){
            if(this.categoryType == "all" || i.getCategoria().toLowerCase().compareTo(this.categoryType) == 0) {
                this.imageListToSend.add(i);
            }
        }
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    /********************************************************************************************************/
    /** Methods */

    @Override
    public void paintComponent(Graphics g){
        //inicializations
        super.paintComponent(g);

        //boxes
        this.vertexSelectorX.setBounds((int) (this.getWidth()/2 - this.vertexSelectorX.getSize().getWidth()), 6, 70, 18);
        this.vertexSelectorX.setVisible(true);
        this.vertexSelectorY.setBounds(this.getWidth()/2 + 5, 6, 70, 18);
        this.vertexSelectorY.setVisible(true);

        if(this.imageList != null) {
            //inicializa charts
            if (this.monthsPieChartPanel == null) {
                this.monthsPieChartPanel = new MonthChartPanel(ChartFactory.createPieChart("Chart", new DefaultPieDataset(), true, true, false));
                this.add(this.monthsPieChartPanel);
            }
            if (this.scatterTestPanel == null) {
                this.scatterTestPanel = new ScatterPlotPanel(ChartFactory.createPieChart("Chart", new DefaultPieDataset(), true, true, false));
                this.add(this.scatterTestPanel);
            }
            if (this.parallelCoordPanel == null) {
                this.parallelCoordPanel = new ParallelCoordPanel();
                this.add(this.parallelCoordPanel);
            }

            // month pie chart
            if(selected == 0) {
                this.monthsPieChartPanel.setImageList(this.imageListToSend);
                this.monthsPieChartPanel.setBounds(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                this.monthsPieChartPanel.updatePieData();
            }
            // scatter plot
            if(selected == 1) {
                this.scatterTestPanel.setImageList(this.imageListToSend);
                this.scatterTestPanel.setBounds(5, 25, this.getWidth() - 10, this.getHeight() - 30);
                this.scatterTestPanel.UpdateScatterDataset(this.xAxis, this.yAxis, this.selectedImage);
            }
            // linear lines
            if(selected == 2){
                this.parallelCoordPanel.setImageList(this.imageListToSend);
                this.parallelCoordPanel.setSelected(this.selectedImage);
                this.parallelCoordPanel.setBounds(5, 25, this.getWidth() - 10, this.getHeight() - 30);
                this.parallelCoordPanel.repaint();
            }

        }
    }

    public void selectImage(ImageInfo image){
        this.selectedImage = image;
        this.repaint();
    }

}
