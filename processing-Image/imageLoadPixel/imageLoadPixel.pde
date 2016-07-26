PImage img;
PImage[] imgList;
Table table;
String[] list;
int count = 0;

void setup() {
  size(400, 500);
  
  String path = sketchPath("");  
  File dir = new File(path);
  list = dir.list();
  
  table = new Table();
  table.addColumn("id");
  table.addColumn("red");
  table.addColumn("green");
  table.addColumn("blue");
  table.addColumn("RGB");
  table.addColumn("hue(360)");
  table.addColumn("sat(100)");
  table.addColumn("brit(100)");
  table.addColumn("altura");
  table.addColumn("largura");
  table.addColumn("ano");
  table.addColumn("mes");
  table.addColumn("categoria");
  table.addColumn("autor");
  table.addColumn("Museum Latitude");
  table.addColumn("Museum Longitude");
  table.addColumn("nome");
  
  if (list == null) {
    println("Folder does not exist or cannot be accessed.");
  } else {
    for(int i = 0; i < list.length; i++){
      if (list[i].toLowerCase().endsWith("jpg") || list[i].toLowerCase().endsWith("jpeg")) {
        img = loadImage(list[i]);
        calculate(i);
        count++;
      }
    }
  } 
  
  saveTable(table,"./dataset.csv");
  println("Salvo");
}

void calculate(int pos){
  img.loadPixels();
  float rTotal = 0, gTotal = 0, bTotal = 0, hTotal = 0, sTotal = 0, lTotal = 0;
  
  for (int y = 0; y < img.height; y++) {
    for (int x = 0; x < img.width; x++) {
      int imageLoc = x + y*img.width;
      // The functions red(), green(), and blue() pull out the 3 color components from a pixel.
      colorMode(RGB,255,255,255);
      float r = red(img.pixels[imageLoc]);
      float g = green(img.pixels[imageLoc]);
      float b = blue(img.pixels[imageLoc]);
      rTotal += r;
      gTotal += g;
      bTotal += b;
      colorMode(HSB,360,100,100);
      float h = hue(img.pixels[imageLoc]);
      float s = saturation(img.pixels[imageLoc]);
      float l = brightness(img.pixels[imageLoc]);
      hTotal += h;
      sTotal += s;
      lTotal += l;
    }
  }
  
  int nPixels = img.width * img.height;
  rTotal = rTotal/nPixels;
  gTotal = gTotal/nPixels;
  bTotal = bTotal/nPixels;
  hTotal = hTotal/nPixels;
  sTotal = sTotal/nPixels;
  lTotal = lTotal/nPixels;
  
  float total = sqrt(pow(rTotal,2) + pow(gTotal,2) + pow(bTotal,2));
  
  TableRow newRow = table.addRow();
  newRow.setInt("id",table.getRowCount());
  newRow.setFloat("red",rTotal);
  newRow.setFloat("green",gTotal);
  newRow.setFloat("blue",bTotal);
  newRow.setFloat("RGB",total);
  newRow.setFloat("hue(360)",hTotal);
  newRow.setFloat("sat(100)",sTotal);
  newRow.setFloat("brit(100)",lTotal);
  newRow.setFloat("altura",1.0);
  newRow.setFloat("largura",1.0);
  newRow.setInt("ano",1);
  newRow.setInt("mes",1);
  newRow.setString("categoria","not defined");
  newRow.setString("nome",list[pos]);
  newRow.setString("autor","someone");
  newRow.setFloat("Museum Latitude", 0.0);
  newRow.setFloat("Museum Longitude", 0.0);
}

void draw() {
  //image(img,0,0);
}