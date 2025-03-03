package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings({"deprecation", "unchecked"})
/**
 * Represents the power up at a visual level
 * @author Mattia
 * 
 */
public class PowerUpView extends MaterialView{
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] powers;

	/**
	 * Assigns values passed as args to attributes
	 */
	public PowerUpView(String entity_name, View view) {
		super(entity_name, view);
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
		int[] values= models.get(name);
		
		if (imagesLoaded) {
			if (values != null) {
				brush.drawImage(powers[values[4]], values[0], values[1], null);
			}
		}
	}

	@Override
	/**
	 * Loads all the sprites in their own array
	 */
	protected void loadImages() {	
		
		try {
			powers= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/power-up/blue candy.png")),
					ImageIO.read(getClass().getResourceAsStream("/power-up/pink candy.png")),
					ImageIO.read(getClass().getResourceAsStream("/power-up/yellow candy.png")),
					ImageIO.read(getClass().getResourceAsStream("/power-up/shoes.png")),
					ImageIO.read(getClass().getResourceAsStream("/power-up/blue ring.png")),
					ImageIO.read(getClass().getResourceAsStream("/power-up/pink ring.png")),
					ImageIO.read(getClass().getResourceAsStream("/power-up/red ring.png")),
					ImageIO.read(getClass().getResourceAsStream("/power-up/blue lantern.png")),
					ImageIO.read(getClass().getResourceAsStream("/power-up/red lantern.png")),
					ImageIO.read(getClass().getResourceAsStream("/power-up/yellow lantern.png")),
			};
			imgResizer(powers, values[2], values[3]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
