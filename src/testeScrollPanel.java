import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import static java.awt.Image.SCALE_SMOOTH;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Created by Vinicius on 15/05/2016.
 */
public class testeScrollPanel extends JPanel implements Observer{
    private AbstractAction loadFile;
    private ImageChooser imageChooser;
    private String orderType;
    public boolean loaded;
    private MainPanel mainPanel;

    /************************************************************************************/
    public testeScrollPanel(MainPanel mainPanel) {
        super();
        this.imageChooser = new ImageChooser();
        this.orderType = "name";
        this.mainPanel = mainPanel;
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
                    setImagesDisplay();
                    testeScrollPanel.this.repaint();
                }
                if (returnVal == JFileChooser.CANCEL_OPTION){
                    JOptionPane.showMessageDialog(testeScrollPanel.this, "Open command cancelled by user.");
                }
            }
        };
        this.loaded = false;
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
        if(this.orderType != orderType){
            this.orderType = orderType;
            
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
            setImagesDisplay();            
            this.repaint();
        }
    }

    /************************************************************************************/
    
    public void setImagesDisplay(){
        int squareWidth = (6 * this.getWidth()) / 10;
        int squareHeight = squareWidth;
        int squareLeft = (2 * this.getWidth())/ 10;
        int listHeight = 0;
        
        int cont = 0;
        for(ImageInfo i : imageChooser.getImageList()){
            try {
                BufferedImage image = ImageIO.read(new File(i.getName()));
                // seta no list panel
                if(image.getWidth() > image.getHeight()){
                    i.setImg(image.getScaledInstance(squareWidth, -1, SCALE_SMOOTH));
                } else {
                    i.setImg(image.getScaledInstance(-1, squareHeight, SCALE_SMOOTH));
                }
            } catch (IOException ex) {
                System.out.println("Não encontrou a imagem " + i.getName());
            }
            i.setWidth(squareWidth);
            i.setHeight(squareHeight);
            i.setX(squareLeft);
            listHeight = 10+squareWidth*cont+cont*10;
            i.setY(listHeight);
            cont++;
        }
        this.setPreferredSize(new Dimension(150,listHeight+200));
        this.loaded = true;
    }
    
    @Override
    public void paintComponent(Graphics g){
        this.removeAll();
        super.paintComponent(g);
        if(this.loaded){
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(this.imageChooser.getImageList().get(0).getName()));
                // seta no main panel
                this.mainPanel.setImg(image);
                this.mainPanel.repaint();
            } catch (IOException ex) {
                System.out.println("ERRO");
            }
        }
        this.loaded = false;
        drawImageList(g);
    }
	
    public void drawImageList(Graphics g){
        for(ImageInfo i : imageChooser.getImageList()){
            g.drawImage(i.getImg(), i.getX(), i.getY(), this);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}
