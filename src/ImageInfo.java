import java.awt.*;

/**
 * Created by Pichau on 15/05/2016.
 */
public class ImageInfo {
    private String name;
    private Image img;

    public ImageInfo(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }
}
