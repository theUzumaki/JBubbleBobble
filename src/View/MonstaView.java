package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings("deprecation")
/**
 * Represents the monsta at a visual level
 * @author Mattia
 * 
 */
public class MonstaView extends EntityView {
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] left, right;

	/**
	 * Assigns values passed as args to attributes
	 */
	public MonstaView(String entity_name, View view) {
		super(entity_name, view);
	}

	@SuppressWarnings("unchecked")
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

		if (imgTimer> 5) {			
			imgSelector();
			imgTimer= 0;
		}

		if (values!=null) {
			switch (values[4]) {
			case -2: brush.drawImage(left[imgSelector], values[0], values[1], null); break;
			case -1: brush.drawImage(left[imgSelector], values[0], values[1], null); break;
			case 1: brush.drawImage(right[imgSelector], values[0], values[1], null); break;
			case 2: brush.drawImage(right[imgSelector], values[0], values[1], null); break;
			}
		}
		
		imgTimer++;
	}
	/**
	 * Used to alternate the sprites
	 */
	private void imgSelector() {
		imgSelector= (imgSelector + 1) % 2;
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
					ImageIO.read(getClass().getResourceAsStream("/monsta/left 1.png")),
					ImageIO.read(getClass().getResourceAsStream("/monsta/left 2.png")),
			};
			imgResizer(left, values[2], values[3]);
			right= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/monsta/right 1.png")),
					ImageIO.read(getClass().getResourceAsStream("/monsta/right 2.png")),
			};
			imgResizer(right, values[2], values[3]);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
