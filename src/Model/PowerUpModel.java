package Model;

/**
 * Represents the power up at a logic level
 * @author Mattia
 * 
 */
public class PowerUpModel extends MaterialModel{

	/**
	 * Assigns values passed as args to attributes
	 */
	public PowerUpModel(String entity_name, int x, int y, int width, int heigth, int type) {
		super(entity_name, x, y, width, heigth);
		coordinates[4]= type;
	}
	@Override
	/**
	 * Updates the object
	 */
	public void update() {
	}
}
