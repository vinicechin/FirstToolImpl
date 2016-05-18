import javax.swing.*;
import java.awt.*;
import static java.awt.Image.SCALE_SMOOTH;

/**
 * Created by Vinicius on 12/04/2016.
 */
public class MainPanel extends JPanel {
    private int width, height;
    private Image img;

    public MainPanel(int width, int height){
        super();
        this.width = width;
        this.height = height;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        int squareWidth = (6 * this.getWidth()) / 10;
        int squareHeight = squareWidth;
        int squareLeft = (int) ((2.5 * this.getWidth())/ 10);
        
        if(this.img != null){
            Image imgTemp = this.img;
            if(imgTemp.getWidth(this) > imgTemp.getHeight(this))
                imgTemp = imgTemp.getScaledInstance(squareWidth, -1, SCALE_SMOOTH);
            else
                imgTemp = imgTemp.getScaledInstance(-1, squareHeight, SCALE_SMOOTH);
            g.drawImage(imgTemp, squareLeft, squareLeft/2, this);
        }
    }
    
    public void setImg(Image img){
        this.img = img;
    }
}
