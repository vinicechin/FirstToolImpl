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

    public MainPanel(){
        super();
        this.img = null;
    }
    
    @Override
    public void paintComponent(Graphics g){
        //inicializations
        int squareLeft = (int) ((3 * this.getWidth())/ 10);
        super.paintComponent(g);

        //draw image if it exists
        if(this.img != null) {
            g.drawImage(this.img, squareLeft, 20, this);
        }
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
}
