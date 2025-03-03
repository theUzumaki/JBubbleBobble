package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings({"deprecation", "unchecked"})
/**
 * Represents the rock at a visual level
 * @author Mattia
 * 
 */
public class RockView extends EntityView{
	
	/**
	 * Used for sprites turnation
	 */
	private int imgLimit, oldValue= 1;
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] left, right, exploded;	

	/**
	 * Assigns values passed as args to attributes
	 */
	public RockView(String entity_name, View view) {
		super(entity_name, view);
	}

	@Override
	/**
	 * Called by notifyObservers, retrieves the values sent
	 */
	public void update(Observable o, Object arg) {
		models= (Map<String, int[]>)arg;
		values= models.get(name);

		if (values[6] == 1) {
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
 
		if (values != null && Math.abs(values[4]) == 1) { imgLimit= 4; }
		else { imgLimit= 3; }
		
		if (oldValue != Math.abs(values[4])) {
			oldValue= 0;
			imgSelector= -1;
			imgTimer= 16;
		}
		
		if (values != null && Math.abs(values[4]) == 1 && imgTimer > 5) {
			imgSelector();
			imgTimer= 0;
		}
		else if (values != null && imgTimer > 15) {
			imgSelector();
			imgTimer= 0;
		}

		if (imagesLoaded) {
			if (values != null) {
				switch (values[4]) {
				case -1: brush.drawImage(left[imgSelector], values[0], values[1], null); break;
				case 0: brush.drawImage(exploded[imgSelector], values[0], values[1], null); break;
				case 1: brush.drawImage(left[imgSelector], values[0], values[1], null); break;
				}
			}
		}
		
		imgTimer++;
	}
	
	/**
	 * Used to alternate the sprites
	 */
	private void imgSelector() {
		imgSelector= (imgSelector + 1) % imgLimit;
	}
	
	@Override
	/**
	 * Loads all the sprites in their own array
	 */
	protected void loadImages() {
		int[] values= null;			
		values= models.get(name);
		
		try {
			left= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/mighta/rock1.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/rock2.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/rock3.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/rock4.png")),
			};
			imgResizer(left, values[2], values[3]);
			right= new BufferedImage[] {
			};
			imgResizer(right, 2*view.tile, 2*view.tile);
			exploded= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/mighta/rock5.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/rock6.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/rock7.png")),
			};
			imgResizer(exploded, 2*view.tile, 2*view.tile);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

