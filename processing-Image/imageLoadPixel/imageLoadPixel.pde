PImage img;
String nomes[] = {"Self-Portrait_with_Dark_Felt_Hat_at_the_Easel22.jpg",
                  "Self-Portrait2.jpg",
                  "Self-Portrait6.jpg",
                  "Self-Portrait7.jpg",
                  "Van_Gogh_-_Selbstbildnis.jpeg",
                  "Van_Gogh_-_Selbstbildnis_mit_Pfeife.jpeg",
                  "Van_Gogh_Self-Portrait_with_Dark_Felt_Hat_1886.jpg",
                  "Vincent_van_Gogh_-_Self-portrait_with_pipe_-_Google_Art_Project.jpg"};

void setup() {
  size(400, 500);
  for(int i=0; i<nomes.length; i++){
    img = loadImage(nomes[i]);
    calculate(i);
  }
}

void calculate(int pos){
  img.loadPixels();
  float rTotal = 0, gTotal = 0, bTotal = 0, hTotal = 0, sTotal = 0;
  
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
      hTotal += h;
      sTotal += s;
    }
  }
  
  int nPixels = img.width * img.height;
  rTotal = rTotal/nPixels;
  gTotal = gTotal/nPixels;
  bTotal = bTotal/nPixels;
  hTotal = hTotal/nPixels;
  sTotal = sTotal/nPixels;
  
  float total = sqrt(pow(rTotal,2) + pow(gTotal,2) + pow(bTotal,2));
  println("\n" + nomes[pos]);
  println("Media RGB: " + rTotal + " " + gTotal + " " + bTotal + " = " + total);
  println("Hue: " + hTotal + " Saturation: " + sTotal);
}

void draw() {
  image(img,0,0);
}