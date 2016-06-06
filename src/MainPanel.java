import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Image.SCALE_SMOOTH;

/**
 * Created by Vinicius on 12/04/2016.
 */
public class MainPanel extends JPanel {
    private Image img;
    private int year;
    private float pictureWidth;
    private float pictureHeight;
    private Image hsvImage;
    List<Double> hueList;
    List<Double> satList;

    public MainPanel(){
        super();
        this.img = null;
        this.hueList = new ArrayList<>();
        this.satList = new ArrayList<>();

        this.hsvImage = null;
        try {
            this.hsvImage = ImageIO.read(new File("./hsvcircle.png"));
        } catch (IOException e) {
            System.out.println("não achou a imagem HSV");
        }
    }

    public void addHSV(double hue, double sat){
        this.hueList.add(hue);
        this.satList.add(sat);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        double circleLeft = ((7.3 * this.getWidth())/10);
        double circleTop = ((6.3 * this.getHeight())/10);
        double circleSize = ((2.5 * this.getWidth())/10);
        double centerX = circleLeft + circleSize/2;
        double centerY = circleTop + circleSize/2;
        int squareLeft = (int) ((0.5 * this.getWidth())/ 10);
        int altura = 20;

        if(this.img != null) {
            g.drawImage(this.img, squareLeft, 20, this);
            g.setColor(Color.red);
            g.fillRect((int)circleLeft,altura,85,60);
            g.setColor(Color.black);
            g.drawString("Ano: " + String.valueOf(this.year), (int)circleLeft+5, altura+20);
            g.drawString("Largura: " + String.valueOf(this.pictureWidth), (int)circleLeft+5, altura+35);
            g.drawString("Altura: " + String.valueOf(this.pictureHeight), (int)circleLeft+5, altura+50);
        }

        g2d.drawImage(this.hsvImage, (int)circleLeft-15, (int)circleTop-15+2, (int)circleSize+30, (int)circleSize+30, this);
//        g.setColor(new Color(0,0,150));
//        Ellipse2D.Double circle = new Ellipse2D.Double(circleLeft, circleTop, circleSize, circleSize);
//        g2d.draw(circle);

        g.setColor(new Color(0, 0, 0));
        g.drawLine((int)(circleLeft - 10), (int)centerY, (int)(circleLeft + circleSize + 10), (int)centerY);
        g.drawLine((int)centerX, (int)(circleTop - 10), (int)centerX, (int)(circleTop + circleSize + 10));

        g.setColor(new Color(10, 14, 66));
        //desenha um ponto no circulo  (x0 + r cos theta, y0 + r sin theta)
        for(int i=0; i<hueList.size(); i++) {
            double pointSize = 5;
            double radius = circleSize / 2 * (satList.get(i) / 100);
            Ellipse2D.Double p1 = new Ellipse2D.Double((centerX + (radius * Math.cos(hueList.get(i)))) - pointSize / 2, (centerY + (radius * Math.sin(hueList.get(i)))) - pointSize / 2, pointSize, pointSize);
            g2d.fill(p1);
        }
    }

    public List<Double> getHueList() {
        return hueList;
    }

    public List<Double> getSatList() {
        return satList;
    }

    public void setImg(Image img){
        int squareWidth = (7 * this.getWidth()) / 10;
        int squareHeight = squareWidth;
        
        if(img != null){
            if(img.getWidth(this) > img.getHeight(this))
                this.img = img.getScaledInstance(squareWidth, -1, SCALE_SMOOTH);
            else
                this.img = img.getScaledInstance(-1, squareHeight, SCALE_SMOOTH);
        }
    }

    public void setHueList(List<Double> hueList) {
        this.hueList = hueList;
    }

    public void setSatList(List<Double> satList) {
        this.satList = satList;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSize(float w, float h) {
        this.pictureWidth = w;
        this.pictureHeight = h;
    }
}
