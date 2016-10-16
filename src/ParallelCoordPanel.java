import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Vinicius on 05-Oct-16.
 */

public class ParallelCoordPanel extends JPanel{
    private List<ImageInfo> imageList;
    private Double[][] dataMatrix;
    private Double[]   maxArray;
    private Double[]   minArray;
    private String[]   namesArray;
    private ImageInfo  selected;
    private int        selectedIndex;
    private JFormattedTextField minDateSelector;
    private JFormattedTextField maxDateSelector;
    private int        minDate;
    private int        maxDate;

    public ParallelCoordPanel(){
        super();

        this.imageList = null;
        this.selected  = null;
        this.selectedIndex = -1;
        this.minDate   = 1885;
        this.maxDate   = 1887;

        this.minDateSelector  = new javax.swing.JFormattedTextField(new Integer(this.minDate));
        this.add(this.minDateSelector);
        this.minDateSelector.addPropertyChangeListener("value", evt -> {
            if(evt.getNewValue() != null) {
                minDate = (int) evt.getNewValue();
                if(maxDate <= minDate){
                    minDate = maxDate-1;
                    minDateSelector.setText(String.valueOf(minDate));
                }
                setImageList(imageList,selected);
            }
        });
        this.minDateSelector.setVisible(false);

        this.maxDateSelector  = new javax.swing.JFormattedTextField(new Integer(this.maxDate));
        this.add(this.maxDateSelector);
        this.maxDateSelector.addPropertyChangeListener("value", evt -> {
            if(evt.getNewValue() != null) {
                maxDate = (int) evt.getNewValue();
                if(maxDate <= minDate){
                    maxDate = minDate+1;
                    maxDateSelector.setText(String.valueOf(maxDate));
                }
                setImageList(imageList,selected);
            }
        });
        this.maxDateSelector.setVisible(false);
    }

    /******************************************************************************************************************/
    public List<ImageInfo> getImageList() {
        return imageList;
    }

    public ImageInfo getSelected() {
        return selected;
    }

    /******************************************************************************************************************/
    public void setImageList(List<ImageInfo> imageList, ImageInfo selectedImage) {
        double maxYear = 0, maxHeight = 0, maxWidth = 0, maxArea = 0;
        double minYear = 3000, minHeight = 1000, minWidth = 1000;

        this.imageList = imageList;
        this.dataMatrix = new Double[imageList.size()][12];  //"Red", "Green", "Blue", "Hue", "Saturation", "Brightness", "Year", "Month",    "Full Date",   "Height",   "Width",   "Area"
        int cont = 0;                                        // 255,   255,     255,    360,    100,           100,     maxy-1000,  12,     maxyear+1-1000,  maxHeight,   maxWidth,  maxArea
        for(ImageInfo i : imageList){
            this.dataMatrix[cont][0]  = Double.valueOf(i.getRedValue());
            this.dataMatrix[cont][1]  = Double.valueOf(i.getGreenValue());
            this.dataMatrix[cont][2]  = Double.valueOf(i.getBlueValue());
            this.dataMatrix[cont][3]  = Double.valueOf(i.getHueValue());
            this.dataMatrix[cont][4]  = Double.valueOf(i.getSatValue());
            this.dataMatrix[cont][5]  = Double.valueOf(i.getLumValue());
            this.dataMatrix[cont][6]  = (Math.round(i.getYear()) + ((i.getMonth()) / 12.0));
            this.dataMatrix[cont][7]  = Double.valueOf(Math.round(i.getYear()));
            this.dataMatrix[cont][8]  = Double.valueOf(i.getMonth());
            this.dataMatrix[cont][9]  = Double.valueOf(i.getPaintingHeigth());
            this.dataMatrix[cont][10] = Double.valueOf(i.getPaintingWidth());
            this.dataMatrix[cont][11] = Double.valueOf(i.getArea());

            if(this.dataMatrix[cont][7] > maxYear){
                maxYear = this.dataMatrix[cont][7];
            }
            if(this.dataMatrix[cont][7] < minYear){
                minYear = this.dataMatrix[cont][7];
            }

            if(this.dataMatrix[cont][9] > maxHeight) {
                maxHeight = this.dataMatrix[cont][9];
            }
            if(this.dataMatrix[cont][9] < minHeight){
                minHeight = this.dataMatrix[cont][9];
            }

            if(this.dataMatrix[cont][10] > maxWidth) {
                maxWidth = this.dataMatrix[cont][10];
            }
            if(this.dataMatrix[cont][10] < minWidth){
                minWidth = this.dataMatrix[cont][10];
            }

            if(this.dataMatrix[cont][11] > maxArea) {
                maxArea = this.dataMatrix[cont][11];
            }

            if (i == selectedImage) {
                this.selected = i;
                this.selectedIndex = cont;
            }

            cont++;
        }

        if(this.maxDate < (int) maxYear+1) {
            this.maxDate = (int) maxYear+1;
            maxDateSelector.setText(String.valueOf(maxDate));
        }
        if(this.minDate > (int) minYear) {
            this.minDate = (int) minYear;
            minDateSelector.setText(String.valueOf(minDate));
        }

        this.maxArray = new Double[12];
        this.maxArray[0]  = Double.valueOf(255); //red
        this.maxArray[1]  = Double.valueOf(255); //green
        this.maxArray[2]  = Double.valueOf(255); //blue
        this.maxArray[3]  = Double.valueOf(360); //hue
        this.maxArray[4]  = Double.valueOf(100); //sat
        this.maxArray[5]  = Double.valueOf(100); //brit
        this.maxArray[6]  = Double.valueOf(this.maxDate);//Double.valueOf(maxYear+2); //full date
        this.maxArray[7]  = Double.valueOf(this.maxDate);//Double.valueOf(maxYear+1); //year
        this.maxArray[8]  = Double.valueOf(12);  //month
        this.maxArray[9]  = Double.valueOf(maxHeight+1); //height
        this.maxArray[10] = Double.valueOf(maxWidth+1); //width
        this.maxArray[11] = Double.valueOf(maxArea); //area

        this.minArray = new Double[12];
        this.minArray[0]  = Double.valueOf(0); //red
        this.minArray[1]  = Double.valueOf(0); //green
        this.minArray[2]  = Double.valueOf(0); //blue
        this.minArray[3]  = Double.valueOf(0); //hue
        this.minArray[4]  = Double.valueOf(0); //sat
        this.minArray[5]  = Double.valueOf(0); //brit
        this.minArray[6]  = Double.valueOf(this.minDate);//Double.valueOf(minYear-1); //full date
        this.minArray[7]  = Double.valueOf(this.minDate);//Double.valueOf(minYear-1); //year
        this.minArray[8]  = Double.valueOf(1);  //month
        this.minArray[9]  = Double.valueOf(0); //height
        this.minArray[10] = Double.valueOf(0); //width
        this.minArray[11] = Double.valueOf(0); //area

        this.namesArray = new String[12];
        this.namesArray[0]  = "Red";
        this.namesArray[1]  = "Green";
        this.namesArray[2]  = "Blue";
        this.namesArray[3]  = "Hue";
        this.namesArray[4]  = "Sat";
        this.namesArray[5]  = "Brit";
        this.namesArray[6]  = "Date";
        this.namesArray[7]  = "Year";
        this.namesArray[8]  = "Month";
        this.namesArray[9]  = "Height";
        this.namesArray[10]  = "Width";
        this.namesArray[11]  = "Area";

        this.repaint();
    }

    public void setSelected(ImageInfo selected) {
        this.selected = selected;
    }

    /******************************************************************************************************************/
    public void drawDataLine(Graphics2D g, double x1, double y1, double x2, double y2){
        //desenha linha do ponto (x1,y1) ate o ponto (x2,y2)
        g.setStroke(new BasicStroke(2));
        g.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
    }

    public void drawGraph(Graphics2D g, double graphLeft, double graphRight, double graphTop, double graphBottom){
        double graphWidth = graphRight - graphLeft;
        double graphHeight = graphBottom - graphTop;
        graphLeft = graphLeft + graphWidth/24;
        int posY, posX;

        //draw lines
        for(int i = 0; i < 12; i++) {
            posX = (int) (graphLeft + i * (graphWidth / 12));
            g.drawLine(posX, (int) graphBottom, posX, (int) graphTop);
            for (int j = 0; j < 5; j++) {
                posY = (int) (graphTop + j * (graphHeight / 4));
                g.drawLine(posX-2, posY , posX+2, posY);
            }
            if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8) {
                g.drawString(String.valueOf(this.minArray[i].intValue()), posX - 6, (int) (graphBottom + 20));
                g.drawString(String.valueOf(this.maxArray[i].intValue()), posX - 6, (int) (graphTop - 10));
            }
            else {
                g.drawString(String.valueOf(this.minArray[i]), posX - 6, (int) (graphBottom + 20));
                g.drawString(String.valueOf(this.maxArray[i]), posX - 6, (int) (graphTop - 10));
            }

            g.drawString(this.namesArray[i],posX-6,(int)(graphBottom+45));

            if(i == 7){
                this.maxDateSelector.setBounds(posX-6, (int)(graphTop-25), 50, 22);
                this.maxDateSelector.setVisible(true);
                this.minDateSelector.setBounds(posX-6, (int)(graphBottom+5), 50, 22);
                this.minDateSelector.setVisible(true);
            }
        }

        //draw data
        g.setColor(new Color(77, 162, 200));
        for(int i = 0; i < this.dataMatrix.length; i++){
            if(this.selectedIndex != i) {
                for (int j = 0; j < this.dataMatrix[i].length - 1; j++) {
                    double posX1 = (graphLeft + j * (graphWidth / 12));
                    double posX2 = (graphLeft + (j + 1) * (graphWidth / 12));
                    double posY1 = (graphBottom - ((this.dataMatrix[i][j] - this.minArray[j]) * (graphHeight / (this.maxArray[j] - this.minArray[j]))));
                    double posY2 = (graphBottom - ((this.dataMatrix[i][j + 1] - this.minArray[j + 1]) * (graphHeight / (this.maxArray[j + 1] - this.minArray[j + 1]))));

                    drawDataLine(g, posX1, posY1, posX2, posY2);
                }
            }
        }
        g.setColor(new Color(200, 10, 10));
        for(int j = 0; j < this.dataMatrix[this.selectedIndex].length-1; j++){
            double posX1 = (graphLeft   + j     * (graphWidth / 12));
            double posX2 = (graphLeft   + (j+1) * (graphWidth / 12));
            double posY1 = (graphBottom - ((this.dataMatrix[this.selectedIndex][j]- this.minArray[j])   * (graphHeight / (this.maxArray[j] - this.minArray[j]))));
            double posY2 = (graphBottom - ((this.dataMatrix[this.selectedIndex][j+1]- this.minArray[j+1]) * (graphHeight / (this.maxArray[j+1] - this.minArray[j+1]))));

            drawDataLine(g,posX1,posY1,posX2,posY2);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        double graphLeft   = this.getWidth()  - ((9.5 * this.getWidth())/10);
        double graphRight  = this.getWidth()  - ((0.5 * this.getWidth())/10);
        double graphTop    = this.getHeight() - ((9.0 * this.getHeight())/10);
        double graphBottom = this.getHeight() - ((1.0 * this.getHeight())/10);
        Graphics2D g2d   = (Graphics2D)g;

        super.paintComponent(g2d);

        if(imageList != null){
            drawGraph(g2d, graphLeft, graphRight, graphTop, graphBottom);
        }
    }
}
