import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
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

    /************************************************************************************/
    public testeScrollPanel() {
        super();
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
                    setImagesDisplay();
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
    public void loadImageFile(String path) throws IOException{
        /** arrumar para ler a imagem de cada pintura... */
        BufferedImage myPicture = ImageIO.read(new File(path));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        //add(picLabel);
    }
    
    public void setImagesDisplay(){
        int squareWidth = (6 * this.getWidth()) / 10;
        int squareHeight = squareWidth;
        int squareLeft = (2 * this.getWidth())/ 10;
        int listHeight = 0;
        
        int cont = 0;
        for(ImageInfo i : imageChooser.getImageList()){
            try {
                loadImageFile(i.getName());
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
    }
    
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
        drawImageList(g);
        for(ImageInfo i : imageChooser.getImageList()) {
            System.out.println(i.getName() + "\n" + i.getYear() + " " + i.getTotalSize() + " " + i.getColorValue() + "\n");
        }
    }
	
    public void drawImageList(Graphics g){
        for(ImageInfo i : imageChooser.getImageList()){
            g.setColor(Color.red);
            g.fillRect(i.getX(),i.getY(),i.getWidth(),i.getHeight());
            g.setColor(Color.black);
            g.drawRect(i.getX(),i.getY(),i.getWidth(),i.getHeight());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}
