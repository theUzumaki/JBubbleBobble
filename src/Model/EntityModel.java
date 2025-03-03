package Model;

/**
 * Intermediate class for new models, which inherits from ConceptModel
 * @author Mattia
 * 
 */
public abstract class EntityModel extends ConceptModel{
	/**
	 * Used to influence gravity
	 */
	int verticalVelocity= -Model.scale;
	/**
	 * Measure frame passing
	 */
	int timer= 0;
	
	/**
	 * Assigns values passed as args to attributes and adds the object to the static map
	 */
	public EntityModel(String entityName, int x, int y, int width, int heigth) {
		super();
		this.width= width;
		this.heigth= heigth;
		name= entityName;
		coordinates= new int[]{ x, y, width, heigth, 0, 0, 0 };
		models.put(entityName, coordinates);
		
		Model.entities.add(this);
	}
	/**
	 * Method to implement to manage the update for each frame
	 */
	public abstract void update();
}
