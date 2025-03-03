package View;

/**
 * Intermediate class for new views, which inherits from ConceptView
 * @author Mattia
 * 
 */
public abstract class EntityView extends ConceptView{
	
	/**
	 * Adds the object to a list of EntityView
	 */
	public EntityView(String entityName, View view) {
		super(view);
		this.name= entityName;
		
		view.entities.add(this);
	}
}
