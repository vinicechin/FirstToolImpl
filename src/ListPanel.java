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
    private String listShowType;
    private MainPanel mainPanel;
    private GraphPanel graphPanel;
    private DataPanel dataPanel;
    private SelectionController mouseControl;
    private boolean loadImages;
    private boolean bFirst;

    /************************************************************************************/
    public ListPanel(MainPanel mainPanel, GraphPanel graphPanel, DataPanel dataPanel) {
        super();
        this.imageChooser = new ImageChooser();
        this.orderType = "color";
        this.colorOrderType = "RGB";
        this.listShowType = "modified";
        this.mainPanel = mainPanel;
        this.graphPanel = graphPanel;
        this.dataPanel = dataPanel;
        this.imageChooser.addObserver(this);
        this.bFirst = true;

        //add the mouse controllers to the panel
        this.mouseControl = new SelectionController(this);
        this.addMouseListener(mouseControl);
        this.addMouseMotionListener(mouseControl);

        //load a model from a .txt file
        this.loadFile = new AbstractAction("Load File") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("./images"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text", "CSV files (*csv)", "csv");
                chooser.setFileFilter(filter);

                int returnVal = chooser.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    String extension = "";
                    int lastpos = chooser.getSelectedFile().getPath().lastIndexOf('.');
                    if (lastpos > 0) {
                        extension = chooser.getSelectedFile().getPath().substring(lastpos+1);
                    }
                    if(extension.toLowerCase().equals("csv")) {
                        imageChooser.loadCSV(chooser.getSelectedFile().getPath());
                    }
                    if(extension.toLowerCase().equals("txt")) {
                        imageChooser.loadImages(chooser.getSelectedFile().getPath());
                    }

                    loadImages = true;
                    setOrderType(ListPanel.this.orderType);
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

    public String getListShowType() {
        return listShowType;
    }

    public void setOrderType(String orderType) {
        if(this.orderType != orderType || this.loadImages){
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

    public void setListShowType(String listShowType) {
        this.listShowType = listShowType;
        setImagesDisplay();
        this.repaint();
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
    public void loadImages(ImageInfo i, int squareWidth, int squareHeight, float maxWidth, float maxHeight){
        try {
                BufferedImage image = ImageIO.read(new File("./images/" + i.getName()));
                // seta no listpanel
                i.setImgOrig(image);
                if(image.getWidth() > image.getHeight()){
                    i.setImgList(image.getScaledInstance(squareWidth, -1, SCALE_SMOOTH));
                    i.setImgListReal(image.getScaledInstance((int) (squareWidth*i.getPaintingWidth()/maxWidth), -1, SCALE_SMOOTH));
                } else {
                    i.setImgList(image.getScaledInstance(-1, squareHeight, SCALE_SMOOTH));
                    i.setImgListReal(image.getScaledInstance(-1, (int) (squareHeight*i.getPaintingHeigth()/maxHeight), SCALE_SMOOTH));
                }
            } catch (IOException ex) {
                System.out.println("Não encontrou a imagem " + i.getName());
            }
    }
    
    //atualiza main com a imagem selecionada
    public void updateImageMain(ImageInfo image){
        // seta primeira imagem da lista no mainpanel
        this.dataPanel.setDataInfo(image);
        this.mainPanel.selectImage(image);
        this.graphPanel.selectHSV((double) image.getHueValue(), (double) image.getSatValue(), (double) image.getLumValue());
    }
    
    //carrega as imagens quando ouve load de um novo file de imagens
    public void setImagesDisplay(){
        int squareHeight = this.getHeight();
        int squareWidth = squareHeight;
        int squareTop = 0;
        int listWidth = 0;
        float maxWidth = 0;
        float maxHeight = 0;

        if(this.loadImages == true){
            for(ImageInfo i : imageChooser.getImageList()){
                if(i.getPaintingWidth() > maxWidth)
                    maxWidth = i.getPaintingWidth();
                if(i.getPaintingHeigth() > maxHeight)
                    maxHeight = i.getPaintingHeigth();
            }
            this.graphPanel.getImgList().clear();
        }

        for(ImageInfo i : imageChooser.getImageList()){
            if(this.loadImages == true) {
                loadImages(i, squareWidth, squareHeight, maxWidth, maxHeight);
                this.graphPanel.addHSV(i);

                // atualiza charts no main panel
                this.mainPanel.setImageList(this.imageChooser.getImageList());
                this.mainPanel.repaint();
            }
            //seta imageInfo
            if(this.listShowType == "modified") {
                setImageInfo(i, i.getImgList().getWidth(this), i.getImgList().getHeight(this), listWidth, squareTop);
                listWidth += i.getImgList().getWidth(this) + 3;
            }
            else {
                setImageInfo(i, i.getImgListReal().getWidth(this), i.getImgListReal().getHeight(this), listWidth, squareTop);
                listWidth += i.getImgListReal().getWidth(this) + 3;
            }
        }

        this.loadImages = false;
        if(imageChooser.getImageList().size() > 0) {
            if(listWidth > 1270){
                this.setPreferredSize(new Dimension(listWidth+200,100));
            } else {
                this.setPreferredSize(new Dimension(1270,100));
            }
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
            if(this.listShowType == "modified")
                g.drawImage(i.getImgList(), i.getX(), i.getY(), this);
            else
                g.drawImage(i.getImgListReal(), i.getX(), i.getY(), this);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}
