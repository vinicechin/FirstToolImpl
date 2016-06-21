import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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
    private double selectedHue;
    private double selectedSat;

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

    public void selectHSV(double hue, double sat){
        this.selectedHue = hue;
        this.selectedSat = sat;
    }

    public void paintFixed(Graphics2D g, double circleLeft, double circleTop, double circleSize, double centerX, double centerY){        
        int sizeDifImage = 48;
        int deslocDifImage = sizeDifImage/2;
        int deslocYImage = 5;
        g.drawImage(this.hsvImage, (int)circleLeft - deslocDifImage, (int)circleTop - deslocDifImage + deslocYImage, (int)circleSize + sizeDifImage, (int)circleSize + sizeDifImage, this);
//        g.setColor(new Color(0,0,150));
//        Ellipse2D.Double circle = new Ellipse2D.Double(circleLeft, circleTop, circleSize, circleSize);
//        g.draw(circle);

        g.setColor(new Color(0, 0, 0));
        g.drawLine((int)(circleLeft - 10), (int)centerY, (int)(circleLeft + circleSize + 10), (int)centerY);
        g.drawLine((int)centerX, (int)(circleTop - 10), (int)centerX, (int)(circleTop + circleSize + 10));
    }
    
    public void paintPoints(Graphics2D g, double circleLeft, double circleTop, double circleSize, double centerX, double centerY){
        double pointSize, hue, sat, radius, pX, pY;
        Ellipse2D.Double pdraw;
        //Point2D.Double pAnt = null, pTo;
        //Line2D.Double line;

        g.setColor(new Color(10, 10, 150));
        //desenha um ponto no circulo  (x0 + r cos theta, y0 - r sin theta) onde theta é angulo em rad
        for(int i=0; i<hueList.size(); i++) {
            pointSize = 5;
            //pega valores para contruir posição do ponto
            hue = hueList.get(i);
            sat = satList.get(i);
            //controi ponto
            radius = circleSize / 2 * (sat / 100);
            pX = (centerX + (radius * Math.cos(hue/180 * Math.PI)));
            pY = (centerY - (radius * Math.sin(hue/180 * Math.PI)));

            //cria variaveis para desenhar e desenha
            /*if(pAnt != null){
                pTo = pAnt;
                pAnt = new Point2D.Double(pX, pY);
                line = new Line2D.Double(pAnt,pTo);
                g.draw(line);
            } else {
                pAnt = new Point2D.Double(pX, pY);
            }*/

            if(hue == this.selectedHue && sat == this.selectedSat) {
                pdraw = new Ellipse2D.Double(pX - pointSize / 2, pY - pointSize / 2, pointSize+2, pointSize+2);
                g.setColor(new Color(150, 10, 10));
            } else {
                pdraw = new Ellipse2D.Double(pX - pointSize / 2, pY - pointSize / 2, pointSize, pointSize);
            }
            g.fill(pdraw);
            g.setColor(new Color(10, 10, 150));
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        //inicializations
        double circleLeft = this.getWidth() - ((2.2 * this.getWidth())/10);
        double circleTop = this.getHeight() - ((5.0 * this.getHeight())/10);
        double circleSize = ((2.0 * this.getWidth())/10);
        double centerX = circleLeft + circleSize/2;
        double centerY = circleTop + circleSize/2;
        int squareLeft = (int) ((3 * this.getWidth())/ 10);
        int y = 20, x = 20;
        
        //seta para desenhar novamente
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g2d);
        
        //draw circle HSV
        paintFixed(g2d, circleLeft, circleTop, circleSize, centerX, centerY);

        //draw image if it exists
        if(this.img != null) {
            g.drawImage(this.img, squareLeft, 20, this);
            g.setColor(Color.red);
            g.fillRect(x,y,85,60);
            g.setColor(Color.black);
            g.drawString("Ano: " + String.valueOf(this.year), (int)x+5, y+20);
            g.drawString("Largura: " + String.valueOf(this.pictureWidth), (int)x+5, y+35);
            g.drawString("Altura: " + String.valueOf(this.pictureHeight), (int)x+5, y+50);
        }

        //draw the points HSV
        paintPoints(g2d, circleLeft, circleTop, circleSize, centerX, centerY);
    }

    public List<Double> getHueList() {
        return hueList;
    }

    public List<Double> getSatList() {
        return satList;
    }

    public void setImg(Image img){
        int squareHeight = (int)(9.5 * this.getHeight()) / 10;
        int squareWidth = (int)(7 * this.getWidth()) / 10;
        
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
