package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic class for new models
 * @author Mattia 
 * 
 */
public abstract class ConceptModel {
	/**
	 * Array of int used to store all the info about a model
	 */
	int[] coordinates;
	/**
	 * Value of the model
	 */
	int width, heigth;
	/**
	 * Unique name which identifies the object
	 */
	String name;
	/**
	 * Static map with all the values for each object
	 */
	static Map<String, int[]> models= new HashMap<>();
	/**
	 * Adds the model to an array
	 */
	public ConceptModel() {
		Model.models.add(this);
	}
	/**
	 * Sets the model to be removed at next update
	 */
	public void removeModel() {
		coordinates[6]= 1;
	}
}
