
import java.awt.*;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;
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
    private String month;
    private float pictureWidth;
    private float pictureHeight;
    private boolean imageLoaded;
    private JLabel imgWidth;
    private JLabel imgHeight;
    private JLabel imgYear;
    private Image  img;
    private JLabel googleImg;
    private boolean bMaps;
    private Image  manImage;
    private Image  miniImage;
    
    public DataPanel(){
        super();
        this.imageLoaded = false;
        this.bMaps = false;
        this.img = null;
        this.miniImage = null;

        this.googleImg = new JLabel();
        this.add(googleImg);

        imgWidth= new JLabel("Largura: ", JLabel.LEFT);
        imgHeight= new JLabel("Altura: ", JLabel.LEFT);
        imgYear= new JLabel("Data: ", JLabel.LEFT);
        this.add(imgWidth);
        this.add(imgHeight);
        this.add(imgYear);
        this.bMaps = false;

        this.manImage = null;
        try {
            this.manImage = ImageIO.read(new File("./manimage.png"));
        } catch (IOException e) {
            System.out.println("não achou a imagem do homem");
        }

    }

    public void setbMaps(boolean bMaps) {
        this.bMaps = bMaps;
    }


    public void setDataInfo(ImageInfo img){
        int squareHeight = (int)(9.5 * this.getHeight()) / 10;
        int squareWidth = (int)(9.5 * this.getWidth()) / 10;
        double manSize = 170;
        int manWidth  = this.getWidth()/5;
        int manHeight = this.getWidth()/3;

        if(img != null){
            if(this.getHeight() > this.getWidth())
                this.img = img.getImgOrig().getScaledInstance(squareWidth, -1, SCALE_SMOOTH);
            else
                this.img = img.getImgOrig().getScaledInstance(-1, squareHeight, SCALE_SMOOTH);

            //update google maps img
            if(img.getGoogleImg() != null && this.bMaps) {
                this.googleImg.setIcon(new ImageIcon((img.getGoogleImg().getScaledInstance(this.getWidth()-10, this.getHeight()/4, java.awt.Image.SCALE_SMOOTH))));
                this.googleImg.setBounds(5,5,this.getWidth()-10,this.getHeight()/4);
            } else if (!this.bMaps){
                this.googleImg.setBounds(5,5,0,0);
            }
        }
        this.pictureWidth = img.getPaintingWidth();
        this.pictureHeight = img.getPaintingHeigth();
        this.year = img.getYear();
        switch(img.getMonth()){
            case  1: this.month = "janeiro"; break;
            case  2: this.month = "fevereiro"; break;
            case  3: this.month = "março"; break;
            case  4: this.month = "abril"; break;
            case  5: this.month = "maio"; break;
            case  6: this.month = "junho"; break;
            case  7: this.month = "julho"; break;
            case  8: this.month = "agosto"; break;
            case  9: this.month = "setembro"; break;
            case 10: this.month = "outubro"; break;
            case 11: this.month = "novembro"; break;
            case 12: this.month = "dezembro"; break;
        }

        //desenha homem para comparar size
        this.manImage = this.manImage.getScaledInstance(manWidth, manHeight, Image.SCALE_SMOOTH);
        this.miniImage = this.img.getScaledInstance(-1, (int) ((manHeight * this.pictureHeight) / manSize), Image.SCALE_SMOOTH );

        this.imageLoaded = true;
        this.repaint();
    }
    
    @Override
    public void paintComponent(Graphics g){
        //inicializations
        super.paintComponent(g);

        //draw image if it exists
        if(this.img != null) {
            int squareLeft = (int) ((5.0 * this.getWidth())/ 10 - this.img.getWidth(this)/2);
            int top = (this.getHeight() - img.getHeight(this));

            this.imgWidth.setBounds(squareLeft, top - 45, 200, 10);
            this.imgHeight.setBounds(squareLeft, top - 30, 200, 10);
            this.imgYear.setBounds(squareLeft, top - 15, 200, 10);
            g.drawImage(this.img, squareLeft, top, this);

            //desennha mini imagem
            g.drawImage(this.miniImage, this.getWidth()/4+5,10,this);

            if(this.bMaps){
                //embaixo do maps
                g.drawImage(this.manImage, 10, 10 + this.getHeight()/4 + 5,this);
            }else{
                //nao tem maps
                g.drawImage(this.manImage, 10, 10, this);
            }

        }

        //draw data if image was loaded
        if(this.imageLoaded) {
            imgWidth.setText("Largura: " + String.valueOf(this.pictureWidth));
            imgHeight.setText("Altura:     " + String.valueOf(this.pictureHeight));
            imgYear.setText("Data:        " + this.month + "/" + String.valueOf(this.year));
        }
    }
}
