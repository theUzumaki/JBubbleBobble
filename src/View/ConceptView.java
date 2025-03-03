package View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
/**
 * Basic class for new views
 * @author Mattia
 * 
 */
public abstract class ConceptView implements Observer {
	
	/**
	 * Used for sprites turnation
	 */
	int imgSelector= 0, imgTimer= 0;
	
	/**
	 * Arrays of values retrieved by the update
	 */
	int[] values;
	
	/**
	 * Indicates if all the sprites were loaded
	 */
	boolean imagesLoaded;
	
	/**
	 * Unique name of the view
	 */
	String name;
	
	/**
	 * Map used to collects the map sent by the model
	 */
	Map<String, int[]> models= new HashMap<>();
	
	/**
	 * Used to add this object to the list of observers to notify
	 */
	View view;
	
	/**
	 * Adds the item as an observer
	 */
	public ConceptView(View view) {
		view.views.add(this);
		view.addObserver(this);
		this.view= view;
	}
	@Override
	/**
	 * Abstract method to implement in order to manage the input sent by the Observable
	 */
	public abstract void update(Observable o, Object arg);
	
	/**
	 * Abstract method to implement in order to draw on screen
	 */
	public abstract void draw(Graphics brush);
	/**
	 * Abstract method to implement in order to load the sprites in their array
	 */
	protected abstract void loadImages();
	/**
	 * Used to scale the images before runtime, to soften the drawing process
	 */
	protected void imgResizer(BufferedImage[] sprites, int width, int height) {	// scales immediately the images to avoid doing it at runtime
		for (int i= 0; i< sprites.length; i++) {
			BufferedImage scaledImage= new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics g= scaledImage.createGraphics();
			g.drawImage(sprites[i], 0, 0, width, height, null);
			sprites[i]= scaledImage;
		}
	}
}
