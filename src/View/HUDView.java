package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings({"deprecation", "unchecked"})
/**
 * Represents the HUD at a visual level
 * @author Mattia
 * 
 */
public class HUDView extends MaterialView {
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] blue, red, green, white;
	
	/**
	 * Only instance existing as in singleton pattern
	 */
	private static HUDView instance;

	/**
	 * Assigns values passed as args to attributes
	 */
	private HUDView(String entityName, View view) {
		super(entityName, view);
	}
	
	/**
	 * Only method to get the instance
	 */
	static public HUDView getInstance(String entityName, View view) {
		if (instance == null) { instance= new HUDView(entityName, view); }
		return instance;
	}

	@Override
	/**
	 * Called by notifyObservers, retrieves the values sent
	 */
	public void update(Observable o, Object arg) {
		models= (Map<String, int[]>)arg;
		values= models.get(name);
		
		if (values[6] == -1) {
			view.views.remove(this);
		}
		if (!imagesLoaded) {			
			loadImages();
			imagesLoaded= true;
		}
	}

	@Override
	/**
	 * Based on the values draws the sprite
	 */
	public void draw(Graphics brush) {
		String score, highScore;
				
		if (imagesLoaded && view.stage.values != null && view.stage.values[6] > 0) {
			score= String.valueOf(values[5]);
			highScore= String.valueOf(values[4]);
			brush.drawImage(green[0], 5*view.tile, 0, null);
			brush.drawImage(green[1], 6*view.tile, 0, null);
			brush.drawImage(green[2], 7*view.tile, 0, null);
			
			brush.drawImage(red[0], 12*view.tile, 0, null);
			brush.drawImage(red[1], 13*view.tile, 0, null);
			brush.drawImage(red[2], 14*view.tile, 0, null);
			brush.drawImage(red[0], 15*view.tile, 0, null);
			brush.drawImage(red[3], 17*view.tile, 0, null);
			brush.drawImage(red[4], 18*view.tile, 0, null);
			brush.drawImage(red[5], 19*view.tile, 0, null);
			brush.drawImage(red[6], 20*view.tile, 0, null);
			brush.drawImage(red[7], 21*view.tile, 0, null);
			
			int length= score.length();
			for (int i= length; i > 0; i--) {
				char num= score.toCharArray()[i-1];
				brush.drawImage(white[Character.getNumericValue(num)], (8-length+i)*view.tile, view.tile, null);
			}
			
			length= highScore.length();
			for (int i= length; i > 0; i--) {
				char num= highScore.toCharArray()[i-1];
				brush.drawImage(white[Character.getNumericValue(num)], (19-length+i)*view.tile, view.tile, null);
			}
			
			int lives= view.bub.values[6];
			if (lives >= 0) {
				brush.drawImage(white[lives], 0, (view.rows-1)*view.tile, null);
			}
		}
	}

	@Override
	/**
	 * Loads all the sprites in their own array
	 */
	protected void loadImages() {
		int[] values= null;			
		values= models.get(name);
		
		try {
			green= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/hud/1 green.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/u green.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/p green.png")),
			};
			imgResizer(green, values[2], values[3]);
			blue= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/hud/t blue.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/o blue.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/c blue.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/n blue.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/i blue.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/u blue.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/e blue.png")),
			};
			imgResizer(blue, values[2], values[3]);
			red= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/hud/h red.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/i red.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/g red.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/s red.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/c red.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/o red.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/r red.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/e red.png")),
			};
			imgResizer(red, values[2], values[3]);
			white= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/hud/0 white.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/1 white.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/2 white.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/3 white.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/4 white.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/5 white.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/6 white.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/7 white.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/8 white.png")),
					ImageIO.read(getClass().getResourceAsStream("/hud/9 white.png")),
			};
			imgResizer(white, values[2], values[3]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
