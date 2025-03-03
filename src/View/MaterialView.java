package View;

/**
 * Intermediate class for new views, which inherits from ConceptView
 * @author Mattia
 * 
 */
public abstract class MaterialView extends ConceptView{
		
	/**
	 * Adds the object to a list of MaterialView
	 */
	public MaterialView(String entity_name, View view) {
		super(view);
		this.name= entity_name;
		
		view.materials.add(this);
	}
}
