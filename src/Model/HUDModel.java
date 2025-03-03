package Model;

/**
 * Represents the HUD at a logic level
 * @author Mattia
 * 
 */
public class HUDModel extends MaterialModel{
	
	/**
	 * Only instance existing as in singleton pattern
	 */
	private static HUDModel instance;

	/**
	 * calls super constructor
	 */
	private HUDModel(String material_name, int x, int y, int width, int heigth) {
		super(material_name, x, y, width, heigth);
	}
	
	/**
	 * Only method to get the instance
	 */
	static public HUDModel getInstance(String materialName, int x, int y, int width, int heigth) {
		if (instance == null) { instance= new HUDModel(materialName, x, y, width, heigth); }
		return instance;
	}

	@Override
	/**
	 * Updates info about current level
	 */
	public void update() {
		if (Model.stage.oldLevel != Model.stage.newLevel && Model.stage.newLevel > 0) {
			coordinates[6]= coordinates[5];
		}
	}
	
	/**
	 * Add points to display
	 */
	public void addPoints(int new_points) {
		coordinates[5]+= new_points;
	}
	/**
	 * Sets current points back to zero
	 */
	public void resetPoints() {
		coordinates[5]= 0;
	}
	
	/**
	 * If the level starts back, it reset points to the starting ones
	 */
	public void loadOldPoints() {
		coordinates[5]= coordinates[6];
	}
	
	/**
	 * Updates highscore
	 */
	public void setHighScore(int highScore) {
		coordinates[4]= highScore;
	}

}
