import java.awt.*;

/**
 * Created by Vinicius on 15/05/2016.
 */
public class ImageInfo implements Comparable<ImageInfo>{
    private String 	name;
    private float 	year;
    private float	paintingWidth;
    private float	paintingHeigth;

    private float   redValue;
    private float   greenValue;
    private float   blueValue;
    private float   rgbValue;
    private float   hueValue;
    private float   satValue;

    private Image 	imgList;
    private Image   imgListReal;
    private Image   imgOrig;
    private int     width;
    private int     height;
    private int     x;
    private int     y;

    /**********************************************************************************************/
    /** Creator */
    public ImageInfo(float redValue, float greenValue, float blueValue, float rgbValue, float hueValue, float satValue, float pw, float ph, float year, String name){
        this.name = name;
        this.year = year;
        this.paintingWidth = pw;
        this.paintingHeigth = ph;
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
        this.rgbValue = rgbValue;
        this.hueValue = hueValue;
        this.satValue = satValue;
    }

    /**********************************************************************************************/
    /** Getters */
    public String getName() {
        return this.name;
    }

    public Image getImgList() {
        return this.imgList;
    }

    public Image getImgListReal() {
        return imgListReal;
    }

    public Image getImgOrig() {
        return this.imgOrig;
    }

    public float getYear() {
        return this.year;
    }

    public float getRedValue() {
        return redValue;
    }

    public float getGreenValue() {
        return greenValue;
    }

    public float getBlueValue() {
        return blueValue;
    }

    public float getRgbValue() {
        return rgbValue;
    }

    public float getHueValue() {
        return hueValue;
    }

    public float getSatValue() {
        return satValue;
    }

    public float getTotalSize() {
        return this.paintingHeigth * this.paintingWidth;
    }

    public float getPaintingWidth() {
        return paintingWidth;
    }

    public float getPaintingHeigth() {
        return paintingHeigth;
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

    public void setImgListReal(Image imgListReal) {
        this.imgListReal = imgListReal;
    }

    public void setImgOrig(Image img) {
        this.imgOrig = img;
    }

    public void setYear(float year) {
        this.year = year;
    }

    public void setRedValue(float redValue) {
        this.redValue = redValue;
    }

    public void setGreenValue(float greenValue) {
        this.greenValue = greenValue;
    }

    public void setBlueValue(float blueValue) {
        this.blueValue = blueValue;
    }

    public void setRgbValue(float rgbValue) {
        this.rgbValue = rgbValue;
    }

    public void setHueValue(float hueValue) {
        this.hueValue = hueValue;
    }

    public void setSatValue(float satValue) {
        this.satValue = satValue;
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

    public int compareToByColor(ImageInfo o, String colorType) {
        switch (colorType) {
            case "Red":
                return (int) ((this.redValue - o.getRedValue())*100);
            case "Green":
                return (int) ((this.greenValue - o.getGreenValue())*100);
            case "Blue":
                return (int) ((this.blueValue - o.getBlueValue())*100);
            case "RGB":
                return (int) ((this.rgbValue - o.getRgbValue())*100);
            case "HSL":
                return (int) ((this.hueValue - o.getHueValue())*100);
            default:
                return (int) ((this.hueValue - o.getHueValue())*100);
        }
    }

    public int compareToByYear(ImageInfo o) {
        /* For Ascending order*/
        return (int) ((this.year - o.getYear())*100);

        /* For Descending order*/
        //return o.getYear() - this.year;
    }

    public int compareToBySize(ImageInfo o) {
        /* For Ascending order*/
        return (int)((this.getTotalSize() - o.getTotalSize())*100);

        /* For Descending order*/
        //return o.getTotalSize() - this.getTotalSize();
    }

    public boolean contains(Point p){
        //returns true if the point p is inside this picture and false if not

        return (this.x < p.x) && (this.y < p.y) && (this.x + this.width > p.x) && (this.y + this.height > p.y);
    }

    /**********************************************************************************************/

}
