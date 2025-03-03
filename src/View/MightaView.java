package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings("deprecation")
/**
 * Represents the mighta at a visual level
 * @author Mattia
 * 
 */
public class MightaView extends EntityView{
	
	/**
	 * Used for sprites turnation
	 */
	private int imgLimit= 2, oldValue= 1;
	
	/**
	 * Used to keep track of the objects
	 */
	private int rockCounter= 0;
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] left, right, leftfiring, rightfiring;

	/**
	 * Assigns values passed as args to attributes
	 */
	public MightaView(String entity_name, View view) {
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
		
		if (values != null && oldValue != Math.abs(values[4])) {
			imgSelector= 0;
			oldValue= Math.abs(values[4]);
		}

		if (imgTimer > 5 && Math.abs(values[4]) == 1) {			
			imgLimit= 2;
			imgSelector();
			imgTimer= 0;
		}
		else if (imgTimer > 5 && Math.abs(values[4]) != 1) {
			imgLimit= 5;
			imgSelector();
			imgTimer= 0;
		}

		switch (values[4]) {
		case -2: brush.drawImage(leftfiring[imgSelector], values[0], values[1], null); break;
		case -1: brush.drawImage(left[imgSelector], values[0], values[1], null); break;
		case 1: brush.drawImage(right[imgSelector], values[0], values[1], null); break;
		case 2: brush.drawImage(rightfiring[imgSelector], values[0], values[1], null); break;
		}
		
		if (values[5] > rockCounter) {
			rockCounter++;
			String ending= name.substring(6);
			new RockView("rock"+ending, view);
		}
		
		imgTimer++;
	}
	private void imgSelector() {
		imgSelector= (imgSelector + 1) % imgLimit;
	}
	@Override
	/**
	 * Loads all the sprites in their own array
	 */
	protected void loadImages() {
		
		try {
			left= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/mighta/left mighta1.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/left mighta2.png")),
			};
			imgResizer(left, values[2], values[3]);
			right= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/mighta/right mighta1.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/right mighta2.png")),
			};
			imgResizer(right, values[2], values[3]);
			leftfiring= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/mighta/left mighta3.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/left mighta4.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/left mighta5.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/left mighta6.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/left mighta7.png")),
			};
			imgResizer(leftfiring, values[2], values[3]);
			rightfiring= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/mighta/right mighta3.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/right mighta4.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/right mighta5.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/right mighta6.png")),
					ImageIO.read(getClass().getResourceAsStream("/mighta/right mighta7.png")),
			};
			imgResizer(rightfiring, values[2], values[3]);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
