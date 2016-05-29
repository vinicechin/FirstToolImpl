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
    private String colorOrderType;
    private MainPanel mainPanel;
    private SelectionController mouseControl;
    private boolean loadImages;

    /************************************************************************************/
    public ListPanel(MainPanel mainPanel) {
        super();
        this.imageChooser = new ImageChooser();
        this.orderType = "color";
        this.colorOrderType = "HSL";
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
                    loadImages = true;
                    setImagesDisplay();
                    ListPanel.this.repaint();
                }
                if (returnVal == JFileChooser.CANCEL_OPTION){
                    JOptionPane.showMessageDialog(ListPanel.this, "Open command cancelled by user.");
                }
            }
        };
        this.loadImages = false;
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

    public String getColorOrderType() {
        return colorOrderType;
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
                    imageChooser.orderByColor(this.colorOrderType);
                    break;
                default:
                    imageChooser.orderByName();
            }
            setImagesDisplay();            
            this.repaint();
        }
    }

    public void setColorOrderType(String colorOrderType) {
        this.colorOrderType = colorOrderType;
        if(this.orderType == "color") {
            imageChooser.orderByColor(this.colorOrderType);
            setImagesDisplay();
            this.repaint();
        }
    }

    /************************************************************************************/
    //seta parametros da imageInfo na imagem atual
    public void setImageInfo(ImageInfo i, int squareWidth, int squareHeight, int squareLeft, int listHeight){
        i.setWidth(squareWidth);
        i.setHeight(squareHeight);
        i.setX(squareLeft);
        i.setY(listHeight);
    }
    
    //carrega imagens dos arquivos
    public void loadImages(ImageInfo i, int squareWidth, int squareHeight){
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
    }
    
    //atualiza main com a imagem selecionada
    public void updateImageMain(ImageInfo image){
        // seta primeira imagem da lista no mainpanel
        this.mainPanel.setImg(image.getImgOrig());
        this.mainPanel.setSize(image.getPaintingWidth(), image.getPaintingHeigth());
        this.mainPanel.setYear((int)image.getYear());
        this.mainPanel.repaint();
    }
    
    //carrega as imagens quando ouve load de um novo file de imagens
    public void setImagesDisplay(){
        int squareHeight = this.getHeight();
        int squareWidth = squareHeight;
        int squareTop = 0;
        int listWidth = 0;

        for(ImageInfo i : imageChooser.getImageList()){
            if(this.loadImages == true)
                loadImages(i, squareWidth, squareHeight);
            //seta imageInfo
            setImageInfo(i, i.getImgList().getWidth(this), squareHeight, listWidth, squareTop);
            listWidth += i.getImgList().getWidth(this) + 3;
        }
        this.loadImages = false;
        if(imageChooser.getImageList().size() > 0) {
            this.setPreferredSize(new Dimension(listWidth+200,100));
            updateImageMain(imageChooser.getImageList().get(0));
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
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
