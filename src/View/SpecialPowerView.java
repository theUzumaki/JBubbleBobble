package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings({"deprecation", "unchecked"})
/**
 * Represents the special power at a visual level
 * @author Mattia
 *
 */
public class SpecialPowerView extends EntityView {
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] waterCorner, waterEnding, waterFlow, movingLightning;

	/**
	 * Assigns values passed as args to attributes
	 */
	public SpecialPowerView(String entityName, View view) {
		super(entityName, view);
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
			view.removeObserver(this);
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
		
		if (imgTimer > 15) {
			imgTimer= 0;
			imgSelector= (imgSelector + 1) % 4;
		}

		if (imagesLoaded) {
			if (values != null) {
				if (values[5]== 1) {
					brush.drawImage(movingLightning[imgSelector], values[0], values[1], null);
				}
				else {
					if (values[6] >= 2) {							
						brush.drawImage(waterEnding[values[7]], values[0], values[1], null);
					}
					if (values[6] >= 3) {
						brush.drawImage(waterFlow[values[10] % 2], values[8], values[9], null);
					}
					if (values[6] >= 4) {
						brush.drawImage(waterFlow[values[13] % 2], values[11], values[12], null);
					}
					if (values[6] >= 5) {
						brush.drawImage(waterFlow[values[16] % 2], values[14], values[15], null);
					}
					if (values[6] == 6) {
						brush.drawImage(waterEnding[values[19]], values[17], values[18], null);
					}
				}
			}
		}
		
		imgTimer++;
	}

	@Override
	/**
	 * Loads all the sprites in their own array
	 */
	protected void loadImages() {
		int[] values= null;			
		values= models.get(name);
		try {
			waterCorner= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water corner left top.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water corner left bottom.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water corner right top.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water corner right bottom.png")),
			};
			imgResizer(waterCorner, view.tile, view.tile);
			waterEnding= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water start left.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water start top.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water start right.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water start bottom.png")),
			};
			imgResizer(waterEnding, view.tile, view.tile);
			waterFlow= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water orizontal.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water vertical.png")),
			};
			imgResizer(waterFlow, view.tile, view.tile);
			movingLightning= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/special bubble/free lightning 1.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/free lightning 2.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/free lightning 3.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/free lightning 4.png")),
			};
			imgResizer(movingLightning, values[2], values[3]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
