
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import static java.awt.Image.SCALE_SMOOTH;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vinicius
 */
public class GraphPanel extends JPanel {
    private Image hsvImage;
    private Image lumImage;
    List<ImageInfo> imgList;
    private double selectedHue;
    private double selectedSat;
    private double selectedLum;
    private JComboBox<String> vertexSelectorX;
    private JComboBox<String> vertexSelectorY;
    private JLabel originLabel;
    private JLabel xLabel;
    private JLabel yLabel;
    private String xAxis;
    private String yAxis;

    /***************************************************************************************************/
    public GraphPanel(){
        super();
        this.imgList = new ArrayList<>();

        //red: 255 double, green: 255 double, blue: 255 double, hue: 360 double, sat: 100 double, bri: 100 double, year: n double(int), month: 12 int, height: m double, width: i double, total size: m*i double
        this.vertexSelectorX  = new javax.swing.JComboBox<>();
        this.vertexSelectorX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue", "Hue", "Saturation", "Brightness", "Year", "Month", "Full Date", "Height", "Width", "Total Size" }));
        this.vertexSelectorX.setSelectedIndex(0);
        this.add(this.vertexSelectorX);
        this.vertexSelectorX.addItemListener(evt -> VertexSelectorXItemStateChanged(evt));

        this.vertexSelectorY  = new javax.swing.JComboBox<>();
        this.vertexSelectorY.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue", "Hue", "Saturation", "Brightness", "Year", "Month", "Full Date", "Height", "Width", "Total Size" }));
        this.vertexSelectorY.setSelectedIndex(1);
        this.add(this.vertexSelectorY);
        this.vertexSelectorY.addItemListener(evt -> VertexSelectorYItemStateChanged(evt));

        this.originLabel= new JLabel("0", JLabel.LEFT);
        this.xLabel= new JLabel("255", JLabel.LEFT);
        this.yLabel= new JLabel("255", JLabel.LEFT);
        this.add(this.originLabel);
        this.add(this.xLabel);
        this.add(this.yLabel);

        this.xAxis = "Red";
        this.yAxis = "Green";

        this.hsvImage = null;
        this.lumImage = null;
        try {
            this.hsvImage = ImageIO.read(new File("./hsvcircle.png"));
            this.lumImage = ImageIO.read(new File("./lightness.png"));
        } catch (IOException e) {
            System.out.println("não achou alguma das imagens default");
        }
    }

    /***************************************************************************************************/
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

    /***************************************************************************************************/
    public void addHSV(ImageInfo img){
        this.imgList.add(img);
    }

    public void selectHSV(double hue, double sat, double lum){
        this.selectedHue = hue;
        this.selectedSat = sat;
        this.selectedLum = lum;
    }

    /***************************************************************************************************/
    public void paintFixed(Graphics2D g, double circleLeft, double circleTop, double circleSize, double centerX, double centerY, int sizeDifImage){        
        int deslocDifImage = sizeDifImage/2;
        int deslocYImage = 3;
        g.drawImage(this.hsvImage, (int)circleLeft - deslocDifImage, (int)circleTop - deslocDifImage + deslocYImage, (int)circleSize + sizeDifImage, (int)circleSize + sizeDifImage, this);
        g.drawImage(this.lumImage, (int)circleLeft - deslocDifImage/2, (int)circleTop - deslocDifImage + deslocYImage - this.lumImage.getHeight(this), (int)circleSize + sizeDifImage/2, this.lumImage.getHeight(this), this);

        //g.setColor(new Color(0,0,150));
        //Ellipse2D.Double circle = new Ellipse2D.Double(circleLeft, circleTop, circleSize, circleSize);
        //g.draw(circle);

        g.setColor(new Color(0, 0, 0));
        g.drawLine((int)(circleLeft - 10), (int)centerY, (int)(circleLeft + circleSize + 10), (int)centerY);
        g.drawLine((int)centerX, (int)(circleTop - 10), (int)centerX, (int)(circleTop + circleSize + 10));
    }

    /***************************************************************************************************/
    public void paintPoints(Graphics2D g, double circleLeft, double circleTop, double circleSize, double centerX, double centerY, double lumTop){
        double pointSize, hue, sat, lum, radius, pX, pY;
        Ellipse2D.Double point;

        pointSize = 5;

        g.setColor(new Color(10, 10, 150));
        //desenha um ponto no circulo  (x0 + r cos theta, y0 - r sin theta) onde theta é angulo em rad
        for(int i=0; i<imgList.size(); i++) {
            //pega valores para contruir posição do ponto
            hue = imgList.get(i).getHueValue();
            sat = imgList.get(i).getSatValue();
            lum = imgList.get(i).getLumValue();
            //constroi pontos
            radius = circleSize / 2 * (sat / 100);

            //hsv circle point
            pX = (centerX + (radius * Math.cos(hue/180 * Math.PI)));
            pY = (centerY - (radius * Math.sin(hue/180 * Math.PI)));
            point = new Ellipse2D.Double(pX - pointSize / 2, pY - pointSize / 2, pointSize, pointSize);
            g.fill(point);
            //brightness point
            pX = (circleLeft + (circleSize * (lum / 100)));
            pY = (lumTop + this.lumImage.getHeight(this)/5);
            point = new Ellipse2D.Double(pX - pointSize / 2, pY - pointSize / 2, pointSize, pointSize);
            g.fill(point);
        }

        if(imgList.size() > 0) {
            g.setColor(new Color(150, 10, 10));
            radius = circleSize / 2 * (this.selectedSat / 100);
            pX = (centerX + (radius * Math.cos(this.selectedHue / 180 * Math.PI)));
            pY = (centerY - (radius * Math.sin(this.selectedHue / 180 * Math.PI)));
            point = new Ellipse2D.Double(pX - pointSize / 2, pY - pointSize / 2, pointSize + 2, pointSize + 2);
            g.fill(point);

            pX = (circleLeft + (circleSize * (this.selectedLum / 100)));
            pY = (lumTop + this.lumImage.getHeight(this)/5);
            point = new Ellipse2D.Double(pX - pointSize / 2, pY - pointSize / 2, pointSize, pointSize);
            g.fill(point);
        }
    }

    /***************************************************************************************************/
    public void paint2DGraph(Graphics2D g){
        double graph2DSizeX = ((7.0 * this.getWidth())/10);
        double graph2DSizeY = ((3.0 * this.getHeight())/10);
        double graph2dLeft = this.getWidth() - ((5.0 * this.getWidth())/10) - graph2DSizeX/2;
        double graph2dTop = ((0.5 * this.getHeight())/10);

        //boxes
        this.vertexSelectorX.setBounds((int) (this.getWidth()/2 - this.vertexSelectorX.getSize().getWidth()), 6, 70, 18);
        this.vertexSelectorY.setBounds(this.getWidth()/2 + 5, 6, 70, 18);
        //Axis
        originLabel.setBounds((int) graph2dLeft - 20, (int) (graph2dTop + graph2DSizeY) + 5, 45, 15);
        xLabel.setBounds((int) (graph2dLeft + graph2DSizeX) - 5, (int) (graph2dTop + graph2DSizeY) + 5, 45, 15);
        yLabel.setBounds((int) graph2dLeft - 20, (int) graph2dTop - 15, 45, 15);
        g.drawLine((int) graph2dLeft, (int) (graph2dTop + graph2DSizeY), (int) graph2dLeft, (int) graph2dTop); //Y
        g.drawLine((int) graph2dLeft, (int) (graph2dTop + graph2DSizeY), (int) (graph2dLeft + graph2DSizeX), (int) (graph2dTop + graph2DSizeY)); //X

        //sets max values for year, height, width, totalSize
        double maxYear = 0, maxHeight = 0, maxWidth = 0, maxTS = 0;
        double minYear = 2016;
        for(int i=0; i<this.imgList.size(); i++) {
            if(maxYear < Math.round(this.imgList.get(i).getYear()))
                maxYear = Math.round(this.imgList.get(i).getYear());
            if(maxHeight < this.imgList.get(i).getPaintingHeigth())
                maxHeight = this.imgList.get(i).getPaintingHeigth();
            if(maxWidth < this.imgList.get(i).getPaintingWidth())
                maxWidth = this.imgList.get(i).getPaintingWidth();
            if(maxTS < this.imgList.get(i).getTotalSize())
                maxTS = this.imgList.get(i).getTotalSize();
            if(minYear > Math.round(this.imgList.get(i).getYear()))
                minYear = Math.round(this.imgList.get(i).getYear());
        }

        //points
        //red: 255 double, green: 255 double, blue: 255 double, hue: 360 double, sat: 100 double, bri: 100 double, year: n double(int), month: 12 int, height: m double, width: i double, total size: m*i double
        double pointSize = 5;
        double pX = graph2dLeft, pY = graph2dTop;
        Ellipse2D.Double point;
        double maxX, maxY;
        for(int i=0; i<this.imgList.size(); i++) {
            switch (this.xAxis){
                case "Red":         maxX = 255; pX = graph2dLeft + (graph2DSizeX * (this.imgList.get(i).getRedValue()   / maxX)); this.originLabel.setText("0"); this.xLabel.setText("255"); break;
                case "Green":       maxX = 255; pX = graph2dLeft + (graph2DSizeX * (this.imgList.get(i).getGreenValue() / maxX)); this.originLabel.setText("0"); this.xLabel.setText("255"); break;
                case "Blue":        maxX = 255; pX = graph2dLeft + (graph2DSizeX * (this.imgList.get(i).getBlueValue()  / maxX)); this.originLabel.setText("0"); this.xLabel.setText("255"); break;
                case "Hue":         maxX = 360; pX = graph2dLeft + (graph2DSizeX * (this.imgList.get(i).getHueValue()   / maxX)); this.originLabel.setText("0"); this.xLabel.setText("360"); break;
                case "Saturation":  maxX = 100; pX = graph2dLeft + (graph2DSizeX * (this.imgList.get(i).getSatValue()   / maxX)); this.originLabel.setText("0"); this.xLabel.setText("100"); break;
                case "Brightness":  maxX = 100; pX = graph2dLeft + (graph2DSizeX * (this.imgList.get(i).getLumValue()   / maxX)); this.originLabel.setText("0"); this.xLabel.setText("100"); break;
                case "Month":       maxX = 12;  pX = graph2dLeft + (graph2DSizeX * (this.imgList.get(i).getMonth()      / maxX)); this.originLabel.setText("0"); this.xLabel.setText("12"); break;

                case "Year":        pX = graph2dLeft + (graph2DSizeX * ((Math.round(this.imgList.get(i).getYear()) - minYear) / (maxYear - minYear))); this.originLabel.setText(String.valueOf(Math.round(minYear))); this.xLabel.setText(String.valueOf(Math.round(maxYear))); break;
                case "Height":      pX = graph2dLeft + (graph2DSizeX * (this.imgList.get(i).getPaintingHeigth()   / (maxHeight + 5)))    ; this.originLabel.setText("0"); this.xLabel.setText(String.valueOf(maxHeight)); break;
                case "Width":       pX = graph2dLeft + (graph2DSizeX * (this.imgList.get(i).getPaintingWidth()    / (maxWidth + 5)))     ; this.originLabel.setText("0"); this.xLabel.setText(String.valueOf(maxWidth)); break;
                case "Total Size":  pX = graph2dLeft + (graph2DSizeX * (this.imgList.get(i).getTotalSize()        / (maxTS + 5)))        ; this.originLabel.setText("0"); this.xLabel.setText(String.valueOf(maxTS)); break;

                case "Full Date":   maxX = 12;  pX = graph2dLeft + (graph2DSizeX * ( (Math.round(this.imgList.get(i).getYear() - minYear) + this.imgList.get(i).getMonth()/maxX) / (Math.round(maxYear - minYear) + 1) ) ); this.originLabel.setText(String.valueOf(Math.round(minYear)) + "/1"); this.xLabel.setText(String.valueOf(Math.round(maxYear)) + "/12"); break;
            }
            switch (this.yAxis){
                case "Red":         maxY = 255; pY = graph2dTop + graph2DSizeY - (graph2DSizeY * (this.imgList.get(i).getRedValue()   / maxY)); this.yLabel.setText("255"); break;
                case "Green":       maxY = 255; pY = graph2dTop + graph2DSizeY - (graph2DSizeY * (this.imgList.get(i).getGreenValue() / maxY)); this.yLabel.setText("255"); break;
                case "Blue":        maxY = 255; pY = graph2dTop + graph2DSizeY - (graph2DSizeY * (this.imgList.get(i).getBlueValue()  / maxY)); this.yLabel.setText("255"); break;
                case "Hue":         maxY = 360; pY = graph2dTop + graph2DSizeY - (graph2DSizeY * (this.imgList.get(i).getHueValue()   / maxY)); this.yLabel.setText("360"); break;
                case "Saturation":  maxY = 100; pY = graph2dTop + graph2DSizeY - (graph2DSizeY * (this.imgList.get(i).getSatValue()   / maxY)); this.yLabel.setText("100"); break;
                case "Brightness":  maxY = 100; pY = graph2dTop + graph2DSizeY - (graph2DSizeY * (this.imgList.get(i).getLumValue()   / maxY)); this.yLabel.setText("100"); break;
                case "Month":       maxY = 12;  pY = graph2dTop + graph2DSizeY - (graph2DSizeY * (this.imgList.get(i).getMonth()      / maxY)); this.yLabel.setText("12"); break;

                case "Year":        pY = graph2dTop + graph2DSizeY - (graph2DSizeY * ((Math.round(this.imgList.get(i).getYear()) - minYear) / (maxYear - minYear))); this.yLabel.setText(String.valueOf(Math.round(maxYear))); break;
                case "Height":      pY = graph2dTop + graph2DSizeY - (graph2DSizeY * (this.imgList.get(i).getPaintingHeigth() / (maxHeight+5)))        ; this.yLabel.setText(String.valueOf(maxHeight)); break;
                case "Width":       pY = graph2dTop + graph2DSizeY - (graph2DSizeY * (this.imgList.get(i).getPaintingWidth()  / (maxWidth + 5)))       ; this.yLabel.setText(String.valueOf(maxWidth)); break;
                case "Total Size":  pY = graph2dTop + graph2DSizeY - (graph2DSizeY * (this.imgList.get(i).getTotalSize()      / (maxTS + 5)))          ; this.yLabel.setText(String.valueOf(maxTS)); break;

                case "Full Date":   maxY = 12;  pY = graph2dTop + graph2DSizeY - (graph2DSizeY * ( (Math.round(this.imgList.get(i).getYear() - minYear) + this.imgList.get(i).getMonth()/maxY) / (Math.round(maxYear - minYear) + 1) ) ); this.yLabel.setText(String.valueOf(Math.round(maxYear))); break;
            }
            point = new Ellipse2D.Double(pX - pointSize / 2, pY - pointSize / 2, pointSize, pointSize);

            if(this.selectedHue == this.imgList.get(i).getHueValue()){
                g.setColor(new Color(150, 10, 10));
            } else {
                g.setColor(new Color(10, 10, 150));
            }
            g.fill(point);
        }
        g.setColor(new Color(10, 10, 150));
    }

    /***************************************************************************************************/
    @Override
    public void paintComponent(Graphics g){
        //inicializations
        double circleSize = ((7.0 * this.getWidth())/10);
        double circleLeft = this.getWidth() - ((5.0 * this.getWidth())/10) - circleSize/2;
        double circleTop = this.getHeight() - ((0.5 * this.getHeight())/10) - circleSize;
        double lumTop = circleTop - this.lumImage.getHeight(this);
        double centerX = circleLeft + circleSize/2;
        double centerY = circleTop + circleSize/2;
        double drawDif = ((1.2 * this.getWidth())/10);
        
        //seta para desenhar novamente
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g2d);
        
        //draw circle HSV
        paintFixed(g2d, circleLeft, circleTop, circleSize, centerX, centerY, (int)drawDif);

        //draw the points HSV
        paintPoints(g2d, circleLeft, circleTop, circleSize, centerX, centerY, lumTop);

        paint2DGraph(g2d);
    }

    /***************************************************************************************************/
    public List<ImageInfo> getImgList() {
        return imgList;
    }
}
