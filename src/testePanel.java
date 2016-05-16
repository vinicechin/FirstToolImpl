import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Observable;

/**
 * Created by Pichau on 12/04/2016.
 */
public class testePanel extends JPanel {
    private int width, height;
    private ImageInfo img;

    public testePanel(int width, int height){
        super();
        this.width = width;
        this.height = height;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillRect(this.width/4,this.height/4,this.width/2,this.height/2);
        g.setColor(Color.black);
        g.drawRect(this.width/4,this.height/4,this.width/2,this.height/2);
    }
}
