
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
public class DataPanel extends JPanel{
    private int year;
    private float pictureWidth;
    private float pictureHeight;
    private boolean imageLoaded;
    private JLabel imgWidth;
    private JLabel imgHeight;
    private JLabel imgYear;
    
    public DataPanel(){
        super();
        this.imageLoaded = false;

        imgWidth= new JLabel("Largura: ", JLabel.LEFT);
        imgHeight= new JLabel("Altura: ", JLabel.LEFT);
        imgYear= new JLabel("Ano: ", JLabel.LEFT);
        imgWidth.setBounds(5, 25, 200, 10);
        imgHeight.setBounds(5, 40, 200, 10);
        imgYear.setBounds(5, 55, 200, 10);
        this.add(imgWidth);
        this.add(imgHeight);
        this.add(imgYear);
    }
    
    @Override
    public void paintComponent(Graphics g){
        //inicializations
        super.paintComponent(g);

        //draw data if image was loaded
        if(this.imageLoaded) {
            imgWidth.setText("Largura: " + String.valueOf(this.pictureWidth));
            imgHeight.setText("Altura:     " + String.valueOf(this.pictureHeight));
            imgYear.setText("Ano:         " + String.valueOf(this.year));
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
