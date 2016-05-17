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
    private Image 	img;

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
        return name;
    }

    public Image getImg() {
        return img;
    }

    public int getYear() {
        return year;
    }

    public int getColorValue() {
        return colorValue;
    }

    public float getTotalSize() {
        return this.paintingHeigth * this.paintingWidth;
    }

    /** Setters */
    public void setName(String name) {
        this.name = name;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
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
