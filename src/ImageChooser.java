import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.List;

/**
 * Created by Vinicius on 15/05/2016.
 */
public class ImageChooser extends java.util.Observable{
    List<ImageInfo> imageList;

    public ImageChooser(){
        this.imageList = new ArrayList<>();
    }

    //load a model from a file
    public void loadImages(String name){
        try {
            Reader r = null;
            try{
                r = new FileReader(name);
                Scanner s = new Scanner(r);
                s.useLocale(Locale.US);

                //pula comentarios
                s.nextLine();
                s.nextLine();

                //le o numero de imagens do arquivo
                int numImages = s.nextInt();

                //limpa a lista de imagens antes de usar
                this.imageList.clear();

                //adiciona a lista cada imagem e seus parametros
                s.nextLine();
                for(int i=0;i<numImages;i++){
                    float red = s.nextFloat();
                    float green = s.nextFloat();
                    float blue = s.nextFloat();
                    float rgb = s.nextFloat();
                    float hue = s.nextFloat();
                    float sat = s.nextFloat();
                    float pw = s.nextFloat();
                    float ph = s.nextFloat();
                    float year = s.nextFloat();
                    s.skip("          ");
                    ImageInfo imageTemp = new ImageInfo(red, green, blue, rgb, hue, sat, pw, ph, year, s.nextLine());
                    imageList.add(imageTemp);
                }
            } finally {
                if(r != null){
                    r.close();
                }
            }
            this.setChanged();
            this.notifyObservers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void orderByColor(String colorType) {
        Collections.sort(imageList, (img1, img2) -> img1.compareToByColor(img2, colorType));
    }

    public void orderByYear() {
        Collections.sort(imageList, (img1, img2) -> img1.compareToByYear(img2));
    }

    public void orderBySize() {
        Collections.sort(imageList, (img1, img2) -> img1.compareToBySize(img2));
    }

    public void orderByName() {
        Collections.sort(imageList, (img1, img2) -> img1.compareTo(img2));
    }

    public List<ImageInfo> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageInfo> imageList) {
        this.imageList = imageList;
    }
}