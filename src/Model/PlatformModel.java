package Model;

/**
 * Represents the platform at a logic level
 * @author Mattia
 * 
 */
public class PlatformModel extends MaterialModel{

	/**
	 * Assigns values passed as args to attributes
	 */
	public PlatformModel(String entity_name, int x, int y, int width, int heigth, int level) {
		super(entity_name, x, y, width, heigth);
		coordinates[5]= level;
	}
	@Override
	/**
	 * Updates the object
	 */
	public void update() {
	}
}
