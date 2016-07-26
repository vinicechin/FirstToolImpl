import java.io.*;
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
                    float value = s.nextFloat();
                    float ph = s.nextFloat();
                    float pw = s.nextFloat();
                    float year = Math.round(s.nextFloat());
                    int month = s.nextInt();
                    s.skip("     ");
                    ImageInfo imageTemp = new ImageInfo(i+1, red, green, blue, rgb, hue, sat, value, pw, ph, (int)year, month, s.nextLine(), "Not Implemented", "Not Implemented", 0.0, 0.0);
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

    public void loadCSV(String name){
        this.imageList.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(name));
            try{
                String line;
                String cvsSplitBy = ";";
                br.readLine();
                while((line = br.readLine()) != null){
                    String[] data = line.split(cvsSplitBy);
                    int id = Integer.parseInt(data[0]);
                    float red = Float.parseFloat(data[1]);
                    float green = Float.parseFloat(data[2]);
                    float blue = Float.parseFloat(data[3]);
                    float rgb = Float.parseFloat(data[4]);
                    float hue = Float.parseFloat(data[5]);
                    float sat = Float.parseFloat(data[6]);
                    float brit = Float.parseFloat(data[7]);
                    float ph = Float.parseFloat(data[8]);
                    float pw = Float.parseFloat(data[9]);
                    int year = Integer.parseInt(data[10]);
                    int month = Integer.parseInt(data[11]);
                    String categoria = data[12];
                    String autor = data[13];
                    double mLatitude = Double.parseDouble(data[14]);
                    double mLongitude = Double.parseDouble(data[15]);
                    ImageInfo imageTemp = new ImageInfo(id, red, green, blue, rgb, hue, sat, brit, pw, ph, year, month, data[16], categoria, autor, mLatitude, mLongitude);
                    imageList.add(imageTemp);
                }
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            this.setChanged();
            this.notifyObservers();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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