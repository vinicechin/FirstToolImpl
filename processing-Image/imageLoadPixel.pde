PImage img;

void setup() {
  size(400, 500);
  img = loadImage("Vincent_van_Gogh_-_Self-portrait_with_pipe_-_Google_Art_Project.jpg");
  calculate();
}

void calculate(){
  loadPixels(); 
  // Since we are going to access the image's pixels too  
  img.loadPixels();
  float totalPixels;
  float rTotal = 0, gTotal = 0, bTotal = 0;
  
  for (int y = 0; y < img.height; y++) {
    for (int x = 0; x < img.width; x++) {
      int imageLoc = x + y*img.width;
      // The functions red(), green(), and blue() pull out the 3 color components from a pixel.
      float r = red(img.pixels[imageLoc]);
      float g = green(img.pixels[imageLoc]);
      float b = blue(img.pixels[imageLoc]);
      rTotal += r;
      gTotal += g;
      bTotal += b;
    }
  }
  
  totalPixels = rTotal + gTotal + bTotal;
  int t2 = img.width * img.height;
  rTotal = rTotal/t2;
  gTotal = gTotal/t2;
  bTotal = bTotal/t2;
  
  float total = pow(rTotal,2) + pow(gTotal,2) + pow(bTotal,2); 
  
  println(rTotal + " " + gTotal + " " + bTotal + " = " + total);
}

void draw() {
  loadPixels(); 
  // Since we are going to access the image's pixels too  
  img.loadPixels();
  
  for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
      int imageLoc = x + y*img.width;
      int displayLoc = x + y*width;
      
      // The functions red(), green(), and blue() pull out the 3 color components from a pixel.
      float r = red(img.pixels[imageLoc]);
      float g = green(img.pixels[imageLoc]);
      float b = blue(img.pixels[imageLoc]);
      
      
      
      // Set the display pixel to the image pixel
      pixels[displayLoc] =  color(r,g,b);          
    }
  }
  updatePixels();
}