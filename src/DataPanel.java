
import java.awt.*;
import javax.swing.*;

import static java.awt.Image.SCALE_SMOOTH;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vinicius
 */
public class DataPanel extends JPanel{
    private int year;
    private float pictureWidth;
    private float pictureHeight;
    private boolean imageLoaded;
    private JLabel imgWidth;
    private JLabel imgHeight;
    private JLabel imgYear;
    private Image img;
    
    public DataPanel(){
        super();
        this.imageLoaded = false;
        this.img = null;

        imgWidth= new JLabel("Largura: ", JLabel.LEFT);
        imgHeight= new JLabel("Altura: ", JLabel.LEFT);
        imgYear= new JLabel("Ano: ", JLabel.LEFT);
        this.add(imgWidth);
        this.add(imgHeight);
        this.add(imgYear);
    }
    
    @Override
    public void paintComponent(Graphics g){
        //inicializations
        super.paintComponent(g);

        //draw image if it exists
        if(this.img != null) {
            int squareLeft = (int) ((5.0 * this.getWidth())/ 10 - this.img.getWidth(this)/2);
            int top = (this.getHeight() - img.getHeight(this));
            imgWidth.setBounds(squareLeft, top - 45, 200, 10);
            imgHeight.setBounds(squareLeft, top - 30, 200, 10);
            imgYear.setBounds(squareLeft, top - 15, 200, 10);
            g.drawImage(this.img, squareLeft, top, this);
        }

        //draw data if image was loaded
        if(this.imageLoaded) {
            imgWidth.setText("Largura: " + String.valueOf(this.pictureWidth));
            imgHeight.setText("Altura:     " + String.valueOf(this.pictureHeight));
            imgYear.setText("Ano:         " + String.valueOf(this.year));
        }
    }

    public void setImg(Image img){
        int squareHeight = (int)(9.5 * this.getHeight()) / 10;
        int squareWidth = (int)(9.5 * this.getWidth()) / 10;

        if(img != null){
            if(this.getHeight() > this.getWidth())
                this.img = img.getScaledInstance(squareWidth, -1, SCALE_SMOOTH);
            else
                this.img = img.getScaledInstance(-1, squareHeight, SCALE_SMOOTH);
        }
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSize(float w, float h) {
        this.pictureWidth = w;
        this.pictureHeight = h;
    }
    
    public void setImageLoaded(boolean value) {
        this.imageLoaded = value;
    }
    
}
