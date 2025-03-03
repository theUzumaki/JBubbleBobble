package Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.IntStream;

@SuppressWarnings("deprecation")
/**
 * Handles all the other Model package classes
 * @author Mattia
 * 
 */
public class Model extends Observable{
	
	/**
	 * Only instance existing as in singleton pattern
	 */
	private static Model instance;
	
	/**
	 * Stat passed by the GamePanel giving info about screen size
	 */
	static int tile, scale, columns, rows;
	
	/**
	 * Measures frame passing
	 */
	static int timer= 0;
	
	/**
	 * Arrays with all the sub classes of EntityModel
	 */
	static ArrayList<EntityModel> entities= new ArrayList<>();
	
	/**
	 * Arrays with all the sub classes of MaterialModel
	 */
	static ArrayList<MaterialModel> materials= new ArrayList<>();
	
	/**
	 * Arrays with all the sub classes of ConceptModel
	 */
	static ArrayList<ConceptModel> models= new ArrayList<>();
	
	/**
	 * Player instance
	 */
	static BubblunModel bub;
	
	/**
	 * Stage instance, used to set level
	 */
	static StageModel stage;
	
	/**
	 * HUD instance, used to add points or remove them
	 */
	static HUDModel hud;
	
	/**
	 * Menu instance, used to handle user input
	 */
	static MenuModel menu;
	
	/**
	 * Assign values passed by args and generates HUD, stage, menu and player
	 */
	private Model(int tile, int scale, int columns, int rows) {
		Model.tile= tile;
		Model.scale= scale;
		Model.columns= columns;
		Model.rows= rows;
		
		hud= HUDModel.getInstance("hud", 0, 0, tile, tile);
		stage= StageModel.getInstance("stage", 0, 0, 0, 0);
		menu= MenuModel.getInstance("menu");
		bub= BubblunModel.getInstance("bub", 11*tile, (rows-13)*tile, 2*tile, 2*tile);
		ConceptModel.models.put("sound", new int[]{ 0, 0, 0 });
	}
	
	/**
	 * Only method to get the instance
	 */
	public static Model getInstance(int tile, int scale, int columns, int rows) {
		if (instance==null) {
			instance= new Model(tile, scale, columns, rows);
		}
		return instance;
	}
	
	/**
	 * Calls all the update of the other models and removes the one set to be removed
	 */
	public void update(boolean[] keys,
			boolean jump, boolean enter, boolean exit) {
		
		ConceptModel.models.get("sound")[0]= 0;
		ConceptModel.models.get("sound")[1]= 0;
		
		if (stage.newLevel <= 0) { menu.update(exit, enter, keys[22], keys[18], keys[0], keys[3], keys[11]); }
		else { 
			menu.update(exit, enter, keys[22], keys[18], keys[0], keys[3], keys[11]);
			if (bub.coordinates[6] != -1) {				
				bub.update(exit, keys[3], keys[0], jump, keys[10]);
			}
			else {
				stage.setLevel(-2);
				menu.saveUpdate(hud.coordinates[5], false);
				menu.newScore("|"+hud.coordinates[5]);
				hud.setHighScore(menu.highScore);
			}
		}

		boolean levelCleared= false;
		if (!bub.dead && !stage.transition) {
		if (stage.oldLevel > 0 && stage.newLevel > 0) { levelCleared= true; }
		for (int i= 0; i < entities.size(); i++) {
			EntityModel entity= entities.get(i);
			if (entity.coordinates[6] == 1) {
				if (entity.name.startsWith("bubble")) {
					switch (entity.coordinates[5]) {
					case -1: if (bub.bubblePoint) { hud.addPoints(100); }; break;
					case 10: hud.addPoints(2000); break;
					case 11: hud.addPoints(6000); break;
					case 12: hud.addPoints(8000); break;
					}
				}
				models.remove(entity);
				entities.remove(i);
			}
			else {
				entity.update();
			}
			if (entity.name.startsWith("bubble")) {
				if (entity.coordinates[5] > 0) {
					levelCleared= false;
				}
			}
			else if (!entity.name.startsWith("spec")) {
				levelCleared= false;
			}
		}
		if (levelCleared) {
			if (timer == 0) {				
				ConceptModel.models.get("sound")[0]= 1;
				ConceptModel.models.get("sound")[2]= 1;
			}
			timer++;
			if (timer > 120) {
				timer= 0;
				stage.nextLevel();
				menu.saveUpdate(hud.coordinates[5], true);
				hud.setHighScore(menu.highScore);
			}
		}
		}
		else if (stage.transition) stage.update();
		
		for (int i= 0; i < materials.size(); i++) {
			MaterialModel material= materials.get(i);
			if (material.name != "stage" && material.coordinates[6] == -1) {
				models.remove(material);
				materials.remove(i);
			}
			else {
				material.update();
			}
		}
		
		setChanged();
		notifyObservers(ConceptModel.models);
	}
	
	/**
	 * Given a model in input checks if it is colliding with the calling model giving back side of collision
	 */
	public static String collisionDetection(int x, int y, int width, int heigth, ConceptModel model) {
        int left= x;
        int right= x+width;
        int top= y;
        int bottom= y+heigth;
        
        int left_ob= model.coordinates[0];
        int right_ob= model.coordinates[0]+model.width;
        int top_ob= model.coordinates[1];
        int bottom_ob= model.coordinates[1]+model.heigth;
        
        if (left < right_ob && right_ob < right) {
        	if (top < bottom_ob && bottom_ob < bottom) {
        		if ((right_ob - left) > (bottom_ob - top)) {
        			return "top";
        		}
        		else {
        			return "left";
        		}
        	}
        	else if (top < top_ob && top_ob < bottom) {
        		if ((right_ob - left) >= (bottom - top_ob)) {
        			return "bottom";
        		}
        		else {        			
        			return "left";
        		}
        	}
        }
        else if (left < left_ob && left_ob < right) {
        	if (top < bottom_ob && bottom_ob < bottom) {
        		if ((right - left_ob) >= (bottom_ob - top)) {
        			return "top";
        		}
        		else {        			
        			return "right";
        		}
        	}
        	else if (top < top_ob && top_ob < bottom) {
        		if ((right - left_ob) >= (bottom - top_ob)) {
        			return "bottom";
        		}
        		else {        			
        			return "right";
        		}
        	}
        }
        return null;        
	}
	
	/**
	 * Given a model in input checks if it is colliding with the calling model by everykind of overlapsing
	 */
	public static boolean projectileCollision(int x, int y, int width, int heigth, ConceptModel model) {
		boolean orizontal= false, vertical= false;
		if (IntStream.rangeClosed(x +1, x+width -1).anyMatch(value -> model.coordinates[0] <= value && value <= model.coordinates[0]+model.width)) {
			orizontal= true;
		}
		if (orizontal && IntStream.rangeClosed(y, y+heigth).anyMatch(value -> model.coordinates[1] <= value && value <= model.coordinates[1]+model.heigth)) {
			vertical= true;
		}
		return orizontal && vertical;
	}
}
