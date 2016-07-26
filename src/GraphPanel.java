
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

    /***************************************************************************************************/
    public GraphPanel(){
        super();
        this.imgList = new ArrayList<>();

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
    public void addHSV(ImageInfo img){
        this.imgList.add(img);
    }

    public void selectHSV(double hue, double sat, double lum){
        this.selectedHue = hue;
        this.selectedSat = sat;
        this.selectedLum = lum;
        this.repaint();
    }

    /***************************************************************************************************/
    public void paintFixed(Graphics2D g, double circleLeft, double circleTop, double circleSize, double centerX, double centerY, int sizeDifImage){        
        int deslocDifImage = sizeDifImage/2;
        int deslocYImage = 3;
        g.drawImage(this.hsvImage, (int)circleLeft - deslocDifImage, (int)circleTop - deslocDifImage + deslocYImage, (int)circleSize + sizeDifImage, (int)circleSize + sizeDifImage, this);
        g.drawImage(this.lumImage, (int)circleLeft - deslocDifImage/2, (int)circleTop - deslocDifImage + deslocYImage - this.lumImage.getHeight(this), (int)circleSize + sizeDifImage/2, this.lumImage.getHeight(this), this);

//        g.setColor(new Color(0,0,150));
//        Ellipse2D.Double circle = new Ellipse2D.Double(circleLeft, circleTop, circleSize, circleSize);
//        g.draw(circle);

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
    @Override
    public void paintComponent(Graphics g){
        //inicializations
        double circleSize = ((5.0 * this.getWidth())/10);
        double circleLeft = this.getWidth() - ((5.0 * this.getWidth())/10) - circleSize/2;
        double circleTop = this.getHeight() - ((5.0 * this.getHeight())/10) - circleSize/2;
        double lumTop = circleTop - this.lumImage.getHeight(this);
        double centerX = circleLeft + circleSize/2;
        double centerY = circleTop + circleSize/2;
        double drawDif = ((1.0 * this.getWidth())/10);
        
        //seta para desenhar novamente
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g2d);
        
        //draw circle HSV
        paintFixed(g2d, circleLeft, circleTop, circleSize, centerX, centerY, (int)drawDif);

        //draw the points HSV
        paintPoints(g2d, circleLeft, circleTop, circleSize, centerX, centerY, lumTop);
    }

    /***************************************************************************************************/
    public List<ImageInfo> getImgList() {
        return imgList;
    }
}
