package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings({"deprecation", "unchecked"})
/**
 * Represents the stage at a visual level
 * @author Mattia
 *
 */
public class StageView extends MaterialView{
	
	/**
	 * Used to keep track of the objects
	 */
	private int powerUpNum, specBubbleNum;
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] title, menu, gameOver, sprites, white;
	
	/**
	 * Only instance existing as in singleton pattern
	 */
	private static StageView instance;

	/**
	 * Assigns values passed as args to attributes
	 */
	private StageView(String entity_name, View view) {
		super(entity_name, view);
	}
	
	/**
	 * Only method to get the instance
	 */
	static public StageView getInstance(String entityName, View view) {
		if (instance == null) { instance= new StageView(entityName, view); }
		return instance;
	}

	@Override
	/**
	 * Called by notifyObservers, retrieves the values sent
	 */
	public void update(Observable o, Object arg) {
		models= (Map<String, int[]>)arg;
		values= models.get(name);

		if (!imagesLoaded) { loadImages(); imagesLoaded= true; }
		
		if (values[6] == -2 || values[7] == -2) {
			Iterator<ConceptView> j= view.views.iterator();
			while (j.hasNext()) {
				ConceptView view= (ConceptView) j.next();
				if (!view.name.startsWith("stage") && (view.name.startsWith("bubble") || !view.name.startsWith("bub")) &&
						!view.name.startsWith("menu") && !view.name.startsWith("hud")) {
					j.remove();
					this.view.entities.remove(view);
					this.view.materials.remove(view);
					this.view.removeObserver(view);
				}
				specBubbleNum= 0;
			}
		}
	
		if (values[6] > 0) {
			if (values[5] != values[6]) {
				view.imagesLoaded= false;
				for (int i= 0; i< values[0]; i++) {
					new PlatformView("plat"+(i+1), view);
				}
				for (int i= 0; i< values[1]; i++) {
					new ZenView("zen"+(i+1), view);
				}
				for (int i= 0; i< values[2]; i++) {
					new MonstaView("monsta"+(i+1), view);
				}
				for (int i= 0; i< values[3]; i++) {
					new MightaView("mighta"+(i+1), view);
				}
			}
			else if (powerUpNum != values[4]){
				new PowerUpView("power"+(values[4]), view);
				powerUpNum++;
			}
			else if (values[7] != -1 && specBubbleNum != values[7]) {
				if (values[7] % 2 == 1) {					
					new BubbleView("bubble lightning"+(values[7]), view);
				}
				else {
					new BubbleView("bubble water"+(values[7]), view);
				}
				specBubbleNum++;
			}
		}
		else if (values[6] == 0) {
			if (imgTimer % 7 == 0) {
				imgSelector();
			}
		}
		
		imgTimer++;
	}

	@Override
	/**
	 * Based on the values draws the sprite
	 */
	public void draw(Graphics brush) {
		if (values != null) {
			if (values[6] == 0) {
				brush.drawImage(title[imgSelector], values[0], values[1], null);
			}
			else if (values[7] == -1) {
				brush.drawImage(white[13], 10*view.tile, 12*view.tile, null);
				brush.drawImage(white[4], 11*view.tile, 12*view.tile, null);
				brush.drawImage(white[23], 12*view.tile, 12*view.tile, null);
				brush.drawImage(white[19], 13*view.tile, 12*view.tile, null);
				
				brush.drawImage(white[11], 15*view.tile, 12*view.tile, null);
				brush.drawImage(white[4], 16*view.tile, 12*view.tile, null);
				brush.drawImage(white[21], 17*view.tile, 12*view.tile, null);
				brush.drawImage(white[4], 18*view.tile, 12*view.tile, null);
				brush.drawImage(white[11], 19*view.tile, 12*view.tile, null);
				brush.drawImage(white[26], 20*view.tile, 12*view.tile, null);
			}
			else if (values[6] == -1) {
				brush.drawImage(menu[0], 0, 4*view.tile, null);
			}
			else if (values[6] == -2) {
				brush.drawImage(gameOver[0], 0, 4*view.tile, null);
			}
			else if (values[6] == -3) {
				brush.drawImage(sprites[0], 1*view.tile, 1*view.tile, null);
				
				brush.drawImage(white[8], 11*view.tile, 12*view.tile, null);
				brush.drawImage(white[13], 12*view.tile, 12*view.tile, null);
				brush.drawImage(white[18], 13*view.tile, 12*view.tile, null);
				brush.drawImage(white[4], 14*view.tile, 12*view.tile, null);
				brush.drawImage(white[17], 15*view.tile, 12*view.tile, null);
				brush.drawImage(white[19], 16*view.tile, 12*view.tile, null);
				
				brush.drawImage(white[13], 18*view.tile, 12*view.tile, null);
				brush.drawImage(white[0], 19*view.tile, 12*view.tile, null);
				brush.drawImage(white[12], 20*view.tile, 12*view.tile, null);
				brush.drawImage(white[4], 21*view.tile, 12*view.tile, null);
			}
			else if (values[6] == -4) {
				brush.drawImage(white[11], 11*view.tile, 4*view.tile, null);
				brush.drawImage(white[4], 12*view.tile, 4*view.tile, null);
				brush.drawImage(white[0], 13*view.tile, 4*view.tile, null);
				brush.drawImage(white[3], 14*view.tile, 4*view.tile, null);
				brush.drawImage(white[4], 15*view.tile, 4*view.tile, null);
				brush.drawImage(white[17], 16*view.tile, 4*view.tile, null);
				brush.drawImage(white[1], 18*view.tile, 4*view.tile, null);
				brush.drawImage(white[14], 19*view.tile, 4*view.tile, null);
				brush.drawImage(white[0], 20*view.tile, 4*view.tile, null);
				brush.drawImage(white[17], 21*view.tile, 4*view.tile, null);
				brush.drawImage(white[3], 22*view.tile, 4*view.tile, null);
			}
		}
	}
	@Override
	/**
	 * Loads all the sprites in their own array
	 */
	protected void loadImages() {
		if (values != null) {			
			try {
				menu= new BufferedImage[] {
						ImageIO.read(getClass().getResourceAsStream("/stage/menu.png")),
				};
				imgResizer(menu, 32*view.tile, 20*view.tile);
				gameOver= new BufferedImage[] {
						ImageIO.read(getClass().getResourceAsStream("/stage/game over.png")),
				};
				imgResizer(gameOver, 32*view.tile, 20*view.tile);
				sprites= new BufferedImage[] {
						ImageIO.read(getClass().getResourceAsStream("/stage/logo.png")),
				};
				imgResizer(sprites, 14*view.tile, 10*view.tile);
				title= new BufferedImage[] {
						ImageIO.read(getClass().getResourceAsStream("/stage/title1.png")),
						ImageIO.read(getClass().getResourceAsStream("/stage/title2.png")),
						ImageIO.read(getClass().getResourceAsStream("/stage/title3.png")),
						ImageIO.read(getClass().getResourceAsStream("/stage/title4.png")),
				};
				imgResizer(title, 32*view.tile, 28*view.tile);
				white= new BufferedImage[] {
						ImageIO.read(getClass().getResourceAsStream("/menu/a.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/b.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/c.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/d.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/e.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/f.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/g.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/h.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/i.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/j.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/k.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/l.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/m.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/n.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/o.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/p.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/q.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/r.png")),				
						ImageIO.read(getClass().getResourceAsStream("/menu/s.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/t.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/u.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/v.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/w.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/x.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/y.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/z.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/!.png")),
				};
				imgResizer(white, view.tile, view.tile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Used to alternate the sprites
	 */
	private void imgSelector() {
		imgSelector= (imgSelector + 1) % 4;
	}

}
