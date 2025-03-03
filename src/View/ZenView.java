package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings("deprecation")
/**
 * Represents the zen chan at a visual level
 * @author Mattia
 * 
 */
public class ZenView extends EntityView {
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] left, right;

	/**
	 * Assigns values passed as args to attributes
	 */
	public ZenView(String entity_name, View view) {
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
		if (!imagesLoaded) {			
			loadImages();
			imagesLoaded= true;
		}
		if (models.get(name)[6] == 1) {
			view.views.remove(this);
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
		
		switch (values[4]) {
		case -1: brush.drawImage(left[imgSelector], values[0], values[1], null); break;
		case 1: brush.drawImage(right[imgSelector], values[0], values[1], null); break;
		}
		
		imgTimer++;
	}
	/**
	 * Used to alternate the sprites
	 */
	private void imgSelector() {
		imgSelector= (imgSelector + 1) % 4;
	}
	@Override
	/**
	 * Loads all the sprites in their own array
	 */
	protected void loadImages() {
		
		try {
			left= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/zen-chan/left1.png")),
					ImageIO.read(getClass().getResourceAsStream("/zen-chan/left2.png")),
					ImageIO.read(getClass().getResourceAsStream("/zen-chan/left3.png")),
					ImageIO.read(getClass().getResourceAsStream("/zen-chan/left4.png")),
			};
			imgResizer(left, values[2], values[3]);
			right= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/zen-chan/right1.png")),
					ImageIO.read(getClass().getResourceAsStream("/zen-chan/right2.png")),
					ImageIO.read(getClass().getResourceAsStream("/zen-chan/right3.png")),
					ImageIO.read(getClass().getResourceAsStream("/zen-chan/right4.png")),
			};
			imgResizer(right, values[2], values[3]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
