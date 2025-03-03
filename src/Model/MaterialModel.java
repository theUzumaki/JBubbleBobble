package Model;

/**
 * Intermediate class for new models, which inherits from ConceptModel
 * @author Mattia 
 * 
 */
public abstract class MaterialModel extends ConceptModel{
	
	/**
	 * Assigns values passed as args to attributes and adds the object to the static map
	 */
	public MaterialModel(String material_name, int x, int y, int width, int heigth) {
		super();
		this.width= width;
		this.heigth= heigth;
		name= material_name;
		coordinates= new int[]{ x, y, width, heigth, 0, 0, 0, 0 };
		models.put(material_name, coordinates);
		
		Model.materials.add(this);
	}
	/**
	 * Method to implement to manage the update for each frame
	 */
	public abstract void update();
}
