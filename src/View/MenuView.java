package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

@SuppressWarnings({"deprecation", "unchecked"})
/**
 * Represents the menu at a visual level
 * @author Mattia
 * 
 */
public class MenuView extends MaterialView {
	
	/**
	 * Indicates if the player is already being respawned
	 */
	private boolean respawn;
	
	/**
	 * Sprites array
	 */
	private BufferedImage[] white, bubble;
	
	/**
	 * Only instance existing as in singleton pattern
	 */
	private static MenuView instance;

	/**
	 * Assigns values passed as args to attributes
	 */
	private MenuView(String entity_name, View view) {
		super(entity_name, view);
	}

	@Override
	/**
	 * Called by notifyObservers, retrieves the values sent
	 */
	public void update(Observable o, Object arg) {
		models= (Map<String, int[]>)arg;
		values= models.get(name);
		
		if (!imagesLoaded) { loadImages(); imagesLoaded= true; }
	}
	
	/**
	 * Only method to get the instance
	 */
	static public MenuView getInstance(String entityName, View view) {
		if (instance == null) { instance= new MenuView(entityName, view); }
		return instance;
	}

	@Override
	/**
	 * Based on the values draws the sprite
	 */
	public void draw(Graphics brush) {

		int[] pswd= {0, 0, 0, 0};
		int pwd= 0;
		
		if (values[2] == 0) { respawn= false; }
		
		if (!respawn && values[2] == 1) {
			view.bub.respawn();
			respawn= true;
		}
		else if (values[4] == -2 && values[2] == 2) {
			Integer num= values[6]/10000000 - 1;
			pwd= num;
			int newValue= values[6]-((num+1)*10000000);				
			int counter= 3;
			while (counter > -1) {
				pswd[counter]= newValue % 26;
				newValue= newValue / 26;
				counter-= 1;
			}
			
		}
	
		if (values[4] > 0) {
			brush.drawImage(white[27], 11*view.tile, 13*view.tile, null);
			brush.drawImage(white[24], 12*view.tile, 13*view.tile, null);
			brush.drawImage(white[30], 13*view.tile, 13*view.tile, null);
			brush.drawImage(white[23], 14*view.tile, 13*view.tile, null);
			brush.drawImage(white[13], 15*view.tile, 13*view.tile, null);
			brush.drawImage(white[values[4]], 18*view.tile, 13*view.tile, null);
			
			brush.drawImage(white[27], 11*view.tile, 15*view.tile, null);
			brush.drawImage(white[14], 12*view.tile, 15*view.tile, null);
			brush.drawImage(white[10], 13*view.tile, 15*view.tile, null);
			brush.drawImage(white[13], 14*view.tile, 15*view.tile, null);
			brush.drawImage(white[34], 15*view.tile, 15*view.tile, null);
			brush.drawImage(white[36], 16*view.tile, 15*view.tile, null);
		}
		else if (values[4] == -1) {
			brush.drawImage(bubble[0], values[0]*view.tile + view.tile/2 + view.scale, values[1]*view.tile + view.tile/2, null);
			
			String wins= values[11]+"";
			String losses= values[12]+"";
			int length1= wins.length();
			int length2= losses.length();
			brush.drawImage(white[32], (4)*view.tile, 2*view.tile, null);
			brush.drawImage(white[21], (5+length2)*view.tile, 2*view.tile, null);
			
			for (int i= length1; i > 0; i--) {
				char num= wins.toCharArray()[length1-i];
				brush.drawImage(white[Character.getNumericValue(num)], (5-i)*view.tile, 3*view.tile, null);
			}
			for (int i= length2; i > 0; i--) {
				char num= losses.toCharArray()[length2-i];
				brush.drawImage(white[Character.getNumericValue(num)], (6+length2-i)*view.tile, 3*view.tile, null);
			}
		}
		else if (values[4] == -2) {
			brush.drawImage(white[pwd + 10], 19*view.tile, 17*view.tile - view.tile/3 + view.scale, null);
			brush.drawImage(white[pswd[0] + 10], 20*view.tile, 17*view.tile - view.tile/3 + view.scale, null);
			brush.drawImage(white[pswd[1] + 10], 21*view.tile, 17*view.tile - view.tile/3 + view.scale, null);
			brush.drawImage(white[pswd[2] + 10], 22*view.tile, 17*view.tile - view.tile/3 + view.scale, null);
			brush.drawImage(white[pswd[3] + 10], 23*view.tile, 17*view.tile - view.tile/3 + view.scale, null);
		}
		else if (values[4] == -3) {
			if (values[7] < 0) values[7]= 24-values[7];
			if (values[8] < 0) values[8]= 24-values[8];
			if (values[9] < 0) values[9]= 24-values[9];
			
			brush.drawImage(white[(values[7] % 26) + 10], 14*view.tile, 17*view.tile, null);
			brush.drawImage(white[(values[8] % 26) + 10], 16*view.tile, 17*view.tile, null);
			brush.drawImage(white[(values[9] % 26) + 10], 18*view.tile, 17*view.tile, null);
		}
		else if (values[4] == -4 && values[21] != 0) {
			brush.drawImage(white[(values[7]) + 10], 12*view.tile, 6*view.tile, null);
			brush.drawImage(white[(values[8]) + 10], 13*view.tile, 6*view.tile, null);
			brush.drawImage(white[(values[9]) + 10], 14*view.tile, 6*view.tile, null);
			brush.drawImage(white[(values[11]) + 10], 12*view.tile, 8*view.tile, null);
			brush.drawImage(white[(values[12]) + 10], 13*view.tile, 8*view.tile, null);
			brush.drawImage(white[(values[13]) + 10], 14*view.tile, 8*view.tile, null);
			brush.drawImage(white[(values[15]) + 10], 12*view.tile, 10*view.tile, null);
			brush.drawImage(white[(values[16]) + 10], 13*view.tile, 10*view.tile, null);
			brush.drawImage(white[(values[17]) + 10], 14*view.tile, 10*view.tile, null);
			brush.drawImage(white[(values[19]) + 10], 12*view.tile, 12*view.tile, null);
			brush.drawImage(white[(values[20]) + 10], 13*view.tile, 12*view.tile, null);
			brush.drawImage(white[(values[21]) + 10], 14*view.tile, 12*view.tile, null);
			brush.drawImage(white[(values[23]) + 10], 12*view.tile, 14*view.tile, null);
			brush.drawImage(white[(values[24]) + 10], 13*view.tile, 14*view.tile, null);
			brush.drawImage(white[(values[25]) + 10], 14*view.tile, 14*view.tile, null);
			
			int j= 0;
			while (j < 5) {
				String score= values[10+4*j]+"";
				int length= score.length();
				for (int i= length; i > 0; i--) {
					char num= score.toCharArray()[length-i];
					brush.drawImage(white[Character.getNumericValue(num)], (23-i)*view.tile, (6+2*j)*view.tile, null);
				}
				j++;
			}
		}
		
		if (values[5] == -2) {
			if (values[6] < 0) values[6]= 23-values[6];
			if (values[7] < 0) values[7]= 23-values[7];
			if (values[8] < 0) values[8]= 23-values[8];
			if (values[9] < 0) values[9]= 23-values[9];
			if (values[10] < 0) values[10]= 23-values[10];
			
			brush.drawImage(white[(values[6] % 26) + 10], 10*view.tile, 23*view.tile, null);
			brush.drawImage(white[(values[7] % 26) + 10], 12*view.tile, 23*view.tile, null);
			brush.drawImage(white[(values[8] % 26) + 10], 14*view.tile, 23*view.tile, null);
			brush.drawImage(white[(values[9] % 26) + 10], 16*view.tile, 23*view.tile, null);
			brush.drawImage(white[(values[10] % 26) + 10], 18*view.tile, 23*view.tile, null);
		}
	}

	@Override
	/**
	 * Loads all the sprites in their own array
	 */
	protected void loadImages() {
		if (values != null) {			
			try {
				white= new BufferedImage[] {
						ImageIO.read(getClass().getResourceAsStream("/menu/0.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/1.png")),				
						ImageIO.read(getClass().getResourceAsStream("/menu/2.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/3.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/4.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/5.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/6.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/7.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/8.png")),
						ImageIO.read(getClass().getResourceAsStream("/menu/9.png")),
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
				bubble= new BufferedImage[] {
						ImageIO.read(getClass().getResourceAsStream("/menu/bubble.png")),
				};
				imgResizer(bubble, 2*view.tile, 2*view.tile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
