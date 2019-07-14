package graphics;

import java.awt.*;

public interface IDrawable {
	 public final static String PICTURE_PATH = "..\\src\\Pictures\\";
	 public void loadImages();
	 public void drawObject (Graphics g);
	 public String getColor();	 
}

