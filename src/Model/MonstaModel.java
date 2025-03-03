package Model;

/**
 * Represents the monsta at a logic level
 * @author Mattia
 * 
 */
public class MonstaModel extends EntityModel {

	/**
	 * Assigns values passed as args to attributes
	 */
	public MonstaModel(String entity_name, int x, int y, int width, int heigth) {
		super(entity_name, x, y, width, heigth);
		coordinates[4]= 1;
	}

	@Override
	/**
	 * Updates the position and status of the object and checks for eventual collisions
	 */
	public void update() {		
		if (coordinates[1] > Model.tile*Model.rows) { coordinates[1]= 0; }
		else if (coordinates[1] < 0) { coordinates[1]= Model.tile*Model.rows; }
		
		switch (coordinates[4]) {
		case -2: coordinates[0]-= Model.scale; coordinates[1]+= Model.scale; break;
		case -1: coordinates[0]-= Model.scale; coordinates[1]-= Model.scale; break;
		case 1: coordinates[0]+= Model.scale; coordinates[1]-= Model.scale; break;
		case 2: coordinates[0]+= Model.scale; coordinates[1]+= Model.scale; break;
		}
		
		if (coordinates[0] < Model.tile) {
			coordinates[0]+= Model.scale;
			coordinates[4]= -coordinates[4]; }
		else if (coordinates[0] > Model.tile*(Model.columns-3)) {
			coordinates[0]-= Model.scale;
			coordinates[4]= -coordinates[4]; }
		timer++;
		
		String collision;
		for (ConceptModel model : Model.models) {
			if (model.name.startsWith("plat")) {
				collision= Model.collisionDetection(coordinates[0], coordinates[1], width, heigth, model);
				if (!model.name.startsWith("bubble") && collision != null) {
					switch (collision) {
					case "top": if (coordinates[4] < 0) { coordinates[4]= -2; } else { coordinates[4]= 2; }break;
					case "bottom": if (coordinates[4] < 0) { coordinates[4]= -1; } else { coordinates[4]= 1; }break;
					case "right": coordinates[4]= -coordinates[4]; break;
					case "left": coordinates[4]= -coordinates[4]; break;
					}
				}
			}
		}
		
		ConceptModel.models.replace(name, coordinates);
	}

}
