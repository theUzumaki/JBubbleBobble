package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings({ "deprecation", "unchecked"})
/**
 * Represents the bubblun at a visual level
 * @author Mattia
 * 
 */
public class BubblunView extends EntityView{
	
	/**
	 * Used for sprites turnation
	 */
	private int bubbleCounter= 0, bubbleTimer, imgLimit= 2,
			oldValue1= 6, oldValue2= 0, invulnerabilityOffset;
	/**
	 * Measures time before the player stops flashing
	 */
	private int invTimer= 180;
	
	/**
	 * Indicates if the player is firing a bubble
	 */
	private boolean bubbling= false;
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] left, standLeft,
				right, standRight,
				jumpLeft, jumpRight,
				fallLeft, fallRight,
				bubbleLeft, bubbleRight,
				start, insideBubble;
	
	/**
	 * img to be drawn
	 */
	private BufferedImage img= null;
	
	/**
	 * Only instance existing as in singleton pattern
	 */
	private static BubblunView instance;

	/**
	 * Assigns values passed as args to attributes
	 */
	private BubblunView(String entity_name, View view) {
		super(entity_name, view);
	}
	
	/**
	 * Only method to get the instance
	 */
	static public BubblunView getInstance(String entityName, View view) {
		if (instance == null) { instance= new BubblunView(entityName, view); }
		return instance;
	}
	
	@Override
	/**
	 * Called by notifyObservers, retrieves the values sent
	 */
	public void update(Observable o, Object arg) {
		models= (Map<String, int[]>)arg;
		values= models.get(name);

		if (models.get(name)[6] == -1) {
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
		
		if (oldValue1 != values[4]) {
			imgSelector= 0;
			oldValue1= values[4];
		}
		if (values[6] > -1 && oldValue2 != values[6]) {
			invulnerabilityOffset= 2;
			invTimer= 0;
			oldValue2= values[6];
		}
		else if (invTimer < 180 && (invTimer % 30 == 0)) {
			if (invulnerabilityOffset == 2) { invulnerabilityOffset= 0; }
			else { invulnerabilityOffset= 2; }
		}
		else if (invTimer % 30 == 0) {
			invulnerabilityOffset= 0;
			}
		
		
		if (values[4] == -6) {
			imgLimit= 8;
		}
		else {
			imgLimit= 2;
		}
		
		if (imgLimit == 2 && imgTimer> 10) {			
			imgSelector();
			imgTimer= 0;
		}
		else if (imgLimit == 8 && imgTimer > 8) {
			imgSelector();
			imgTimer= 0;
		}
		
		if (Math.abs(values[4]) == 5) {
			if (values[4] == 5) { img= bubbleRight[0]; }
			else { img= bubbleLeft[0]; }
			bubbling= true; bubbleTimer= 0;
		}

		if (bubbling) {
			bubbleTimer++;
			if (bubbleTimer > 5) { bubbling= false; }
		}
		else {
			switch (values[4]) {
			case -6: img= insideBubble[imgSelector + invulnerabilityOffset]; break;
			case -4: img= fallLeft[imgSelector + invulnerabilityOffset]; break;
			case -3: img= jumpLeft[imgSelector + invulnerabilityOffset]; break;
			case -2: img= standLeft[imgSelector + invulnerabilityOffset]; break;
			case -1: img= left[imgSelector + invulnerabilityOffset]; break;
			case 0: img= standRight[imgSelector + invulnerabilityOffset]; break;
			case 1: img= right[imgSelector + invulnerabilityOffset]; break;
			case 2: img= standRight[imgSelector + invulnerabilityOffset]; break;
			case 3: img= jumpRight[imgSelector + invulnerabilityOffset]; break;
			case 4: img= fallRight[imgSelector + invulnerabilityOffset]; break;
			case 6: img= start[imgSelector + invulnerabilityOffset]; break;
			}
		}
		
		if (values!= null) {
			brush.drawImage(img, values[0], values[1], null);
			
			if (values[5] > bubbleCounter) {
				bubbleCounter++;
				new BubbleView("bubble"+bubbleCounter, view);
			}
		}
		
		imgTimer++; invTimer++;
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
			standLeft= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/stand_left1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/stand_left2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white stand_left1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white stand_left2.png")),
			};
			imgResizer(standLeft, values[2], values[3]);
			left= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/left1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/left2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white left1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white left2.png")),
			};
			imgResizer(left, values[2], values[3]);
			standRight= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/stand_right1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/stand_right2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white stand_right1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white stand_right2.png")),
			};
			imgResizer(standRight, values[2], values[3]);
			right= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/right1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/right2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white right1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white right2.png")),
			};
			imgResizer(right, values[2], values[3]);
			jumpLeft= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/jump_left1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/jump_left2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white jump_left1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white jump_left2.png")),
			};
			imgResizer(jumpLeft, values[2], values[3]);
			jumpRight= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/jump_right1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/jump_right2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white jump_right1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white jump_right2.png")),
			};
			imgResizer(jumpRight, values[2], values[3]);
			fallLeft= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/fall_left1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/fall_left2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white fall_left1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white fall_left2.png")),
			};
			imgResizer(fallLeft, values[2], values[3]);
			fallRight= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/fall_right1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/fall_right2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white fall_right1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/white fall_right2.png")),
			};
			imgResizer(fallRight, values[2], values[3]);
			bubbleLeft= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/bubble left.png")),
			};
			imgResizer(bubbleLeft, values[2], values[3]);
			bubbleRight= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/bubble right.png")),
			};
			imgResizer(bubbleRight, values[2], values[3]);
			start= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/start1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/start2.png")),
			};
			imgResizer(start, values[2], values[3]);
			insideBubble= new BufferedImage[] {
					ImageIO.read(getClass().getResourceAsStream("/bub/bubble1.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/bubble2.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/bubble3.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/bubble4.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/bubble5.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/bubble6.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/bubble7.png")),
					ImageIO.read(getClass().getResourceAsStream("/bub/bubble8.png")),
			};
			imgResizer(insideBubble, 2*values[2], 2*values[3]);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds back the player into the list of object to be drawn
	 */
	public void respawn() {
		boolean inside= false;
		for (ConceptView view : view.views) {
			if (view.name == "bub") {
				inside= true;
			}
		}
		if (!inside) {
			view.views.add(this);
			view.addObserver(this);
		}
	}
}
