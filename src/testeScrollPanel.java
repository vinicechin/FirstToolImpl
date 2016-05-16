import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Pichau on 15/05/2016.
 */
public class testeScrollPanel extends JScrollPane implements Observer{
    private int width, height;
    private AbstractAction loadFile;
    private ImageChooser imageChooser;

    public testeScrollPanel(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        this.imageChooser = new ImageChooser();
        imageChooser.addObserver(this);
        this.repaint();

        //load a model from a .txt file
        this.loadFile = new AbstractAction("Open File") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                chooser.setFileFilter(filter);

                int returnVal = chooser.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    imageChooser.loadImages(chooser.getSelectedFile().getName());
                }
                if (returnVal == JFileChooser.CANCEL_OPTION){
                    JOptionPane.showMessageDialog(testeScrollPanel.this, "Open command cancelled by user.");
                }
            }
        };
    }

    public AbstractAction getLoadFile() {
        return loadFile;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(ImageInfo i : imageChooser.getImageList()) {
            System.out.println(i.getName());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}
