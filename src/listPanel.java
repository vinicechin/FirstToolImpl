import javax.swing.*;
import java.awt.*;

/**
 * Created by Pichau on 16/05/2016.
 */
public class listPanel extends JPanel{
    public listPanel(){
        super(new GridLayout());
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawImageList(g);
    }

    public void drawImageList(Graphics g){
        int squareWidth = (8 * this.getWidth()) / 10;
        int squareHeight = squareWidth;
        int squareLeft = this.getWidth() / 10;

        g.setColor(Color.red);
        g.fillRect(squareLeft,10,squareWidth,squareHeight);
        g.setColor(Color.black);
        g.drawRect(squareLeft,10,squareWidth,squareHeight);

        g.setColor(Color.red);
        g.fillRect(squareLeft,10 + squareWidth * 1,squareWidth,squareHeight);
        g.setColor(Color.black);
        g.drawRect(squareLeft,10 + squareWidth * 1,squareWidth,squareHeight);

        g.setColor(Color.red);
        g.fillRect(squareLeft,10 + squareWidth * 2,squareWidth,squareHeight);
        g.setColor(Color.black);
        g.drawRect(squareLeft,10 + squareWidth * 2,squareWidth,squareHeight);

        g.setColor(Color.red);
        g.fillRect(squareLeft,10 + squareWidth * 3,squareWidth,squareHeight);
        g.setColor(Color.black);
        g.drawRect(squareLeft,10 + squareWidth * 3,squareWidth,squareHeight);

        g.setColor(Color.red);
        g.fillRect(squareLeft,10 + squareWidth * 4,squareWidth,squareHeight);
        g.setColor(Color.black);
        g.drawRect(squareLeft,10 + squareWidth * 4,squareWidth,squareHeight);

        g.setColor(Color.red);
        g.fillRect(squareLeft,10 + squareWidth * 5,squareWidth,squareHeight);
        g.setColor(Color.black);
        g.drawRect(squareLeft,10 + squareWidth * 5,squareWidth,squareHeight);
    }
}
