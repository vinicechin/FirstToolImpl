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
        
        int squareLeft = (int) ((2.5 * this.getWidth())/ 10);
        g.drawImage(this.img, squareLeft, squareLeft/2, this);
    }
    
    public void setImg(Image img){
        int squareWidth = (6 * this.getWidth()) / 10;
        int squareHeight = squareWidth;
        
        if(img != null){
            if(img.getWidth(this) > img.getHeight(this))
                this.img = img.getScaledInstance(squareWidth, -1, SCALE_SMOOTH);
            else
                this.img = img.getScaledInstance(-1, squareHeight, SCALE_SMOOTH);
        }
    }
}
