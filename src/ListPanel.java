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
    private MainPanel mainPanel;
    private SelectionController mouseControl;
    private boolean loadImages;

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
        this.mainPanel.setColor(image.getColorValue());
        this.mainPanel.setSize(image.getPaintingWidth(), image.getPaintingHeigth());
        this.mainPanel.setYear(image.getYear());
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
            if(this.loadImages == true)
                loadImages(i, squareWidth, squareHeight);
            //seta imageInfo
            listHeight = 10+squareWidth*cont+cont*10;
            setImageInfo(i, squareWidth, squareHeight, squareLeft, listHeight);
            cont++;
        }
        this.loadImages = false;
        this.setPreferredSize(new Dimension(150,listHeight+200));
        updateImageMain(imageChooser.getImageList().get(0));
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
