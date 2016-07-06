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

    JEditorPane jep;

    public MainPanel(){
        super();
        jep = new JEditorPane();
        jep.setEditable(false);

        try {
            File file = new File("E://Dados Vini//Facul//Ufrgs//TCC//InteliJProjects//ImageShowTool//mainpanelJS.html");
            jep.setPage(file.toURI().toURL());
        }catch (IOException e) {
            jep.setContentType("text/javascript");
            jep.setText("<html>Could not load</html>");
        }

        this.add(jep);
    }

    @Override
    public void paintComponent(Graphics g){
        //inicializations
        super.paintComponent(g);
        jep.setBounds(10,10,this.getWidth()-20,this.getHeight()-20);
    }

}
