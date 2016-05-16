import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

                //read the number of vertexes and edges from the file
                int numImages = s.nextInt();

                //clear the vertex and edge lists from the model
                this.imageList.clear();

                //for each vertex read it from the file in the right order of parameters and add it to the model
                s.nextLine();
                for(int i=0;i<numImages;i++){
                    ImageInfo imageTemp = new ImageInfo(s.nextLine());
                    System.out.println(imageTemp.getName());
                    imageList.add(imageTemp);
                    this.notifyObservers();
                }
            } finally {
                if(r != null){
                    r.close();
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ImageInfo> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageInfo> imageList) {
        this.imageList = imageList;
    }
}