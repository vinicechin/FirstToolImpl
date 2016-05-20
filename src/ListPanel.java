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
public class ListPanel extends JPanel implements Observer{
    private AbstractAction loadFile;
    private ImageChooser imageChooser;
    private String orderType;
    public boolean loaded;
    private MainPanel mainPanel;
    private SelectionController mouseControl;

    /************************************************************************************/
    public ListPanel(MainPanel mainPanel) {
        super();
        this.imageChooser = new ImageChooser();
        this.orderType = "name";
        this.mainPanel = mainPanel;
        this.imageChooser.addObserver(this);

        //add the mouse controllers to the panel
        this.mouseControl = new SelectionController(this, mainPanel);
        this.addMouseListener(mouseControl);
        this.addMouseMotionListener(mouseControl);

        //load a model from a .txt file
        this.loadFile = new AbstractAction("Load File") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("./images"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                chooser.setFileFilter(filter);

                int returnVal = chooser.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    imageChooser.loadImages(chooser.getSelectedFile().getPath());
                    setImagesDisplay();
                    ListPanel.this.repaint();
                }
                if (returnVal == JFileChooser.CANCEL_OPTION){
                    JOptionPane.showMessageDialog(ListPanel.this, "Open command cancelled by user.");
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

    public ImageChooser getImageChooser() {
        return imageChooser;
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
            rearrangeImageList();            
            this.repaint();
        }
    }

    /************************************************************************************/
    //reordena lista quando ouver um evento de reordenação
    public void rearrangeImageList(){
        int squareWidth = (8 * this.getWidth()) / 10;
        int squareHeight = squareWidth;
        int squareLeft = (this.getWidth())/ 10;
        int listHeight = 0;
        
        int cont = 0;
        for(ImageInfo i : imageChooser.getImageList()){
            i.setWidth(squareWidth);
            i.setHeight(squareHeight);
            i.setX(squareLeft);
            listHeight = 10+squareWidth*cont+cont*10;
            i.setY(listHeight);
            cont++;
        }
        this.setPreferredSize(new Dimension(150,listHeight+200));
        // seta primeira imagem da lista no mainpanel
        this.mainPanel.setImg(imageChooser.getImageList().get(0).getImgOrig());
        this.mainPanel.setColor(imageChooser.getImageList().get(0).getColorValue());
        this.mainPanel.setSize(imageChooser.getImageList().get(0).getPaintingWidth(), imageChooser.getImageList().get(0).getPaintingHeigth());
        this.mainPanel.setYear(imageChooser.getImageList().get(0).getYear());
        this.mainPanel.repaint();
    }
    
    //carrega as imagens quando ouve load de um novo file de imagens
    public void setImagesDisplay(){
        int squareWidth = (8 * this.getWidth()) / 10;
        int squareHeight = squareWidth;
        int squareLeft = (this.getWidth())/ 10;
        int listHeight = 0;
        
        int cont = 0;
        for(ImageInfo i : imageChooser.getImageList()){
            try {
                BufferedImage image = ImageIO.read(new File("./images/" + i.getName()));
                // seta no listpanel
                i.setImgOrig(image);
                if(image.getWidth() > image.getHeight()){
                    i.setImgList(image.getScaledInstance(squareWidth, -1, SCALE_SMOOTH));
                } else {
                    i.setImgList(image.getScaledInstance(-1, squareHeight, SCALE_SMOOTH));
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
        // seta primeira imagem da lista no mainpanel
        this.mainPanel.setImg(imageChooser.getImageList().get(0).getImgOrig());
        this.mainPanel.setColor(imageChooser.getImageList().get(0).getColorValue());
        this.mainPanel.setSize(imageChooser.getImageList().get(0).getPaintingWidth(), imageChooser.getImageList().get(0).getPaintingHeigth());
        this.mainPanel.setYear(imageChooser.getImageList().get(0).getYear());
        this.mainPanel.repaint();
    }
    
    @Override
    public void paintComponent(Graphics g){
        this.removeAll();
        super.paintComponent(g);
        drawImageList(g);
    }
	
    public void drawImageList(Graphics g){
        for(ImageInfo i : imageChooser.getImageList()){
            g.drawImage(i.getImgList(), i.getX(), i.getY(), this);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}
