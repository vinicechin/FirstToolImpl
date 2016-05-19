import java.awt.*;

/**
 * Created by Vinicius on 15/05/2016.
 */
public class ImageInfo implements Comparable<ImageInfo>{
    private String 	name;
    private int 	year;
    private int 	fileWidth;
    private int 	fileHeigth;
    private float	paintingWidth;
    private float	paintingHeigth;
    private int		colorValue;
    private Image 	imgList;
    private Image       imgOrig;
    private int         width;
    private int         height;
    private int         x;
    private int         y;

    /**********************************************************************************************/
    /** Creator */
    public ImageInfo(int fw, int fh, float pw, float ph, int year, String name){
        this.name = name;
        this.year = year;
        this.fileWidth = fw;
        this.fileHeigth = fh;
        this.paintingWidth = pw;
        this.paintingHeigth = ph;
        this.colorValue = 0;
    }

    /**********************************************************************************************/
    /** Getters */
    public String getName() {
        return this.name;
    }

    public Image getImgList() {
        return this.imgList;
    }
    
    public Image getImgOrig() {
        return this.imgOrig;
    }

    public int getYear() {
        return this.year;
    }

    public int getColorValue() {
        return this.colorValue;
    }

    public float getTotalSize() {
        return this.paintingHeigth * this.paintingWidth;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }

    /** Setters */
    public void setName(String name) {
        this.name = name;
    }

    public void setImgList(Image img) {
        this.imgList = img;
    }
    
    public void setImgOrig(Image img) {
        this.imgOrig = img;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
    }
    
    public void setWidth(int width){
        this.width = width;
    }
    
    public void setHeight(int height){
        this.height = height;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }

    /**********************************************************************************************/
    /** Comparadores */
    @Override
    public int compareTo(ImageInfo o) {
        return name.compareTo(o.getName());
    }

    public int compareToByColor(ImageInfo o) {
        /* For Ascending order*/
        return this.colorValue - o.getColorValue();

        /* For Descending order*/
        //return o.getColorValue() - this.colorValue;
    }

    public int compareToByYear(ImageInfo o) {
        /* For Ascending order*/
        return this.year - o.getYear();

        /* For Descending order*/
        //return o.getYear() - this.year;
    }

    public int compareToBySize(ImageInfo o) {
        /* For Ascending order*/
        return Math.round(this.getTotalSize() - o.getTotalSize());

        /* For Descending order*/
        //return o.getTotalSize() - this.getTotalSize();
    }

    /**********************************************************************************************/

}
