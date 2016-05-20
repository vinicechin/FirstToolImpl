import javax.swing.*;
import java.awt.*;
import static java.awt.Image.SCALE_SMOOTH;

/**
 * Created by Vinicius on 12/04/2016.
 */
public class MainPanel extends JPanel {
    private Image img;
    private int year;
    private float pictureWidth;
    private float pictureHeight;
    private float color;

    public MainPanel(){
        super();
        this.img = null;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        int squareLeft = (int) ((2 * this.getWidth())/ 10);
        if(this.img != null) {
            g.drawImage(this.img, squareLeft, squareLeft/2, this);
            g.drawString("Ano: " + String.valueOf(this.year), 10, 70);
            g.drawString("Largura: " + String.valueOf(this.pictureWidth), 10, 85);
            g.drawString("Altura: " + String.valueOf(this.pictureHeight), 10, 100);
            //g.drawString(String.format("%.2f", this.color), 10, 100);
        }
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

    public void setYear(int year) {
        this.year = year;
    }

    public void setSize(float w, float h) {
        this.pictureWidth = w;
        this.pictureHeight = h;
    }

    public void setColor(float color) {
        this.color = color;
    }
}
