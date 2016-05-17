import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.*;

/**
 * Created by Vinicius on 15/05/2016.
 */
public class testeScrollPanel extends JScrollPane implements Observer{
    private AbstractAction loadFile;
    private ImageChooser imageChooser;
    private String orderType;
    //private listPanel showListPanel;

    /************************************************************************************/
    public testeScrollPanel(JPanel panel) {
        super(panel);
        this.imageChooser = new ImageChooser();
        this.orderType = "name";
        imageChooser.addObserver(this);

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
                    testeScrollPanel.this.repaint();
                }
                if (returnVal == JFileChooser.CANCEL_OPTION){
                    JOptionPane.showMessageDialog(testeScrollPanel.this, "Open command cancelled by user.");
                }
            }
        };

        this.repaint();
    }

    /************************************************************************************/
    /** getters and setters */
    public AbstractAction getLoadFile() {
        return loadFile;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
        this.repaint();
    }

    /************************************************************************************/
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        switch (this.orderType) {
            case "year":
                imageChooser.orderByYear();
                break;
            case "size":
                imageChooser.orderBySize();
                break;
            case "color":
                imageChooser.orderByColor();
                break;
            default:
                imageChooser.orderByName();
        }
        for(ImageInfo i : imageChooser.getImageList()) {
            System.out.println(i.getName() + "\n" + i.getYear() + " " + i.getTotalSize() + " " + i.getColorValue() + "\n");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}
