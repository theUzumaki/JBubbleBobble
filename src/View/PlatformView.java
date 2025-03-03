package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings("deprecation")
/**
 * Represents the platform at a visual level
 * @author Mattia
 * 
 */
public class PlatformView extends MaterialView {
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] block;

	/**
	 * Assigns values passed as args to attributes
	 */
	public PlatformView(String materialName, View view) {
		super(materialName, view);
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
	}
	@Override
	/**
	 * Based on the values draws the sprite
	 */
	public void draw(Graphics brush) {
		brush.drawImage(block[values[5]-1], values[0], values[1], null);
	}
	@Override
	/**
	 * Loads all the sprites in their own array
	 */
	protected void loadImages() {

		try {
			block= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/blocks/1.png")),
					ImageIO.read(getClass().getResourceAsStream("/blocks/2.png")),
					ImageIO.read(getClass().getResourceAsStream("/blocks/3.png")),
					ImageIO.read(getClass().getResourceAsStream("/blocks/4.png")),
					ImageIO.read(getClass().getResourceAsStream("/blocks/5.png")),
					ImageIO.read(getClass().getResourceAsStream("/blocks/6.png")),
					ImageIO.read(getClass().getResourceAsStream("/blocks/7.png")),
					ImageIO.read(getClass().getResourceAsStream("/blocks/8.png")),
			};
			imgResizer(block, view.tile, view.tile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}