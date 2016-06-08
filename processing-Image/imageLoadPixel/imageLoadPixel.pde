PImage img;
String nomes[] = {"439px-Self-Portrait9_Van_Gogh.jpg",
                  "439px-Vincent_Willem_van_Gogh_108.jpg",
                  "450px-Van_Gogh_-_Selbstbildnis_mit_Strohhut_und_Pfeife.jpeg",
                  "454px-Van_Gogh_-_Selbsbildnis13.jpeg",
                  "456px-Van_Gogh_-_Selbstbildnis_mit_Strohhut1.jpeg",
                  "457px-Van_Gogh_Self-Portrait_Autumn_1887.jpg",
                  "463px-Van_Gogh_Self-Portrait_with_Straw_Hat_1887-Detroit.jpg",
                  "474px-Van_Gogh_Self-Portrait_with_Straw_Hat_1887-Metropolitan.jpg",
                  "485px-Vincent_van_Gogh_-_Zelfportret_-_Google_Art_Project.jpg",
                  "Self-Portrait_with_Dark_Felt_Hat_at_the_Easel22.jpg",
                  "Self-Portrait_with_Pipe_and_Glass28.jpg",
                  "Self-Portrait2.jpg",
                  "Self-Portrait6.jpg",
                  "Self-Portrait7.jpg",
                  "Van_Gogh_-_Selbstbildnis.jpeg",
                  "Van_Gogh_-_Selbstbildnis_mit_Pfeife.jpeg",
                  "Van_Gogh_Self-Portrait_with_Dark_Felt_Hat_1886.jpg",
                  "Van_Gogh_Self-Portrait_with_Grey_Felt_Hat_1886-87_Rijksmuseum.jpg",
                  "Vincent_van_Gogh_-_Self-portrait_-_Google_Art_Project.jpg",
                  "Vincent_van_Gogh_-_Self-Portrait_-_Google_Art_Project_(454045).jpg",
                  "Vincent_van_Gogh_-_Self-portrait_-_Google_Art_Project_(nAHHHe2ggxUGyg).jpg",
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
  //println("Media RGB: " + rTotal + " " + gTotal + " " + bTotal + " = " + total);
  //println("Hue: " + hTotal + " Saturation: " + sTotal);
  println(rTotal + " " + gTotal + " " + bTotal + " " + total + " " + hTotal + " " + sTotal);
}

void draw() {
  image(img,0,0);
}