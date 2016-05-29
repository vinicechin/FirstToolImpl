PImage img;
String nomes[] = {"Self-Portrait6.jpg","Vincent_van_Gogh_-_Self-portrait_with_pipe_-_Google_Art_Project.jpg",
                  "Van_Gogh_Self-Portrait_with_Dark_Felt_Hat_1886.jpg","Van_Gogh_-_Selbstbildnis_mit_Pfeife.jpeg",
                  "Van_Gogh_-_Selbstbildnis.jpeg","Self-Portrait_with_Dark_Felt_Hat_at_the_Easel22.jpg"};

void setup() {
  size(400, 500);
  for(int i=0; i<6; i++){
    img = loadImage(nomes[i]);
    calculate(i);
  }
}

void calculate(int pos){
  img.loadPixels();
  float rTotal = 0, gTotal = 0, bTotal = 0, hTotal = 0;
  
  for (int y = 0; y < img.height; y++) {
    for (int x = 0; x < img.width; x++) {
      int imageLoc = x + y*img.width;
      // The functions red(), green(), and blue() pull out the 3 color components from a pixel.
      float r = red(img.pixels[imageLoc]);
      float g = green(img.pixels[imageLoc]);
      float b = blue(img.pixels[imageLoc]);
      float h = hue(img.pixels[imageLoc]);
      rTotal += r;
      gTotal += g;
      bTotal += b;
      hTotal += h;
    }
  }
  
  int nPixels = img.width * img.height;
  rTotal = rTotal/nPixels;
  gTotal = gTotal/nPixels;
  bTotal = bTotal/nPixels;
  hTotal = hTotal/nPixels;
  
  float total = sqrt(pow(rTotal,2) + pow(gTotal,2) + pow(bTotal,2));
  println("\n" + nomes[pos]);
  println("Media RGB: " + rTotal + " " + gTotal + " " + bTotal + " = " + total);
  println("Hue: " + hTotal);
}

void draw() {
  image(img,0,0);
}