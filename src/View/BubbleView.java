package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings({"deprecation", "unchecked"})
/**
 * Represents the bubbles at a visual level
 * @author Mattia
 * 
 */
public class BubbleView extends EntityView{
	
	/**
	 * Indicates if its a special bubble
	 */
	private boolean water, lightning;
	
	/**
	 * Used for sprites turnation
	 */
	private int oldImg= 0, imgLimit= 4, oldValue;
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] waterBubble, lightningBubble,
					bubble, explodedbubble,
					zenbubble, expzenbubble,
					monstabubble, expmonstabubble,
					mightabubble, expmightabubble,
					banana, eggplant, peach,
					two, six, eight;	

	/**
	 * Assigns values passed as args to attributes
	 */
	public BubbleView(String entityName, View view) {
		super(entityName, view);
	}

	@Override
	/**
	 * Called by notifyObservers, retrieves the values sent
	 */
	public void update(Observable o, Object arg) {
		models= (Map<String, int[]>)arg;
		values= models.get(name);
		
		if (models.get(name)[6] == 1) {
			if (lightning) name= "spec lightning"+name.toCharArray()[name.length()-1];
			else if (water) name= "spec water"+name.toCharArray()[name.length()-1];
			if (lightning || water) new SpecialPowerView(name, view);
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

		if (!lightning && values[5] == -11) lightning= true;
		else if (!water && values[5] == -12) water= true;
		
		if (values[5] == 0 && imgTimer > 15 && imgSelector < 3) {
			oldImg= imgSelector;
			imgSelector();
			imgTimer= 0;
		}
		else if (values[5] < -10 && imgTimer > 15) {
			imgSelector();
			imgTimer= 0;
		}
		else if (values[5] >= 4 && imgTimer%5 == 0) { imgSelector(); }
		else if (values[5] >= 1 && imgTimer%30 == 0) { imgSelector(); }
		else if (values[5] == -1 && imgTimer%15 == 0) { imgSelector(); }
		
		if (values[5] == 0 && oldImg != imgSelector) {
			imgResizer(bubble, values[2], values[3]);
		}
		if (oldValue != values[5]) {
			oldValue= values[5];
			imgSelector= 0; imgTimer= 0;
			if (values[5] == -1) { imgLimit= 2; }
			else if (values[5] >= 4) { imgLimit= 4; }
		}
		if (imagesLoaded) {
			switch (values[5]) {
			case -12: brush.drawImage(waterBubble[imgSelector], values[0], values[1], null); break;
			case -11: brush.drawImage(lightningBubble[imgSelector], values[0], values[1], null); break;
			case -1: brush.drawImage(explodedbubble[imgSelector], values[0], values[1], null); break;
			case 0: brush.drawImage(bubble[imgSelector], values[0], values[1], null); break;
			case 1: brush.drawImage(zenbubble[imgSelector], values[0], values[1], null); break;
			case 2: brush.drawImage(monstabubble[imgSelector], values[0], values[1], null); break;
			case 3: brush.drawImage(mightabubble[imgSelector], values[0], values[1], null); break;
			case 4: brush.drawImage(expzenbubble[imgSelector], values[0], values[1], null); break;
			case 5: brush.drawImage(expmonstabubble[imgSelector], values[0], values[1], null); break;
			case 6: brush.drawImage(expmightabubble[imgSelector], values[0], values[1], null); break;
			case 7: brush.drawImage(banana[0], values[0], values[1], null); break;
			case 8: brush.drawImage(eggplant[0], values[0], values[1], null); break;
			case 9: brush.drawImage(peach[0], values[0], values[1], null); break;
			case 10: brush.drawImage(two[0], values[0], values[1], null); break;
			case 11: brush.drawImage(eight[0], values[0], values[1], null); break;
			case 12: brush.drawImage(six[0], values[0], values[1], null); break;
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
			waterBubble= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water 1.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water 2.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water 3.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/water 4.png")),
			};
			imgResizer(waterBubble, values[2], values[3]);
			lightningBubble= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/special bubble/lightning 1.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/lightning 2.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/lightning 3.png")),
					ImageIO.read(getClass().getResourceAsStream("/special bubble/lightning 4.png")),
			};
			imgResizer(lightningBubble, values[2], values[3]);
			bubble= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/3.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/4.png")),
			};
			imgResizer(bubble, values[2], values[3]);
			explodedbubble= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/5.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/6.png")),
			};
			imgResizer(explodedbubble, 2*view.tile, 2*view.tile);
			zenbubble= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/zen1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/zen2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/zen3.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/zen4.png")),
			};
			imgResizer(zenbubble, 2*view.tile, 2*view.tile);
			expzenbubble= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/zen5.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/zen6.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/zen7.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/zen8.png")),
			};
			imgResizer(expzenbubble, 2*view.tile, 2*view.tile);
			monstabubble= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/monsta1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/monsta2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/monsta3.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/monsta4.png")),
			};
			imgResizer(monstabubble, 2*view.tile, 2*view.tile);
			expmonstabubble= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/monsta5.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/monsta6.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/monsta7.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/monsta8.png")),
			};
			imgResizer(expmonstabubble, 2*view.tile, 2*view.tile);
			mightabubble= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/mighta1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/mighta2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/mighta3.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/mighta4.png")),
			};
			imgResizer(mightabubble, 2*view.tile, 2*view.tile);
			expmightabubble= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/mighta5.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/mighta6.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/mighta7.png")),
					ImageIO.read(getClass().getResourceAsStream("/bubble/mighta8.png")),
			};
			imgResizer(expmightabubble, 2*view.tile, 2*view.tile);
			banana= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/banana.png")),
			};
			imgResizer(banana, 2*view.tile, 2*view.tile);
			eggplant= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/eggplant.png")),
			};
			imgResizer(eggplant, 2*view.tile, 2*view.tile);
			peach= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/peach.png")),
			};
			imgResizer(peach, 2*view.tile, 2*view.tile);
			two= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/2000.png")),
			};
			imgResizer(two, 2*view.tile, 2*view.tile);
			six= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/6000.png")),
			};
			imgResizer(six, 2*view.tile, 2*view.tile);
			eight= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bubble/8000.png")),
			};
			imgResizer(eight, 2*view.tile, 2*view.tile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
