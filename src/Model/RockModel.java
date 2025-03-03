package Model;

/**
 * Represents the rock at a logic level
 * @author Mattia
 * 
 */
public class RockModel extends EntityModel{
	
	/**
	 * Indicates velocity of the rock
	 */
	private int orizontal_velocity= 3*Model.scale/2;
			
	/**
	 * Measures frame passing till remove
	 */
	private int death_timer;

	/**
	 * Assigns values passed as args to attributes
	 */
	public RockModel(String entity_name, int x, int y, int width, int heigth, int verse) {
		super(entity_name, x, y, width, heigth);
		coordinates[4]= verse;
	}

	@Override
	/**
	 * Updates the position and status of the object and checks for eventual collisions
	 */
	public void update() {
		if (death_timer == 29) { coordinates[6]= 1; }
		
		if (coordinates[0] < Model.tile) { coordinates[0]+= Model.scale; orizontal_velocity= 0; coordinates[4]= 0; }
		else if (coordinates[0] > Model.tile*(Model.columns-3)) { coordinates[0]-= Model.scale; orizontal_velocity= 0; coordinates[4]= 0; }

		if (coordinates[4] == 0) {	death_timer++; }
		else { coordinates[0]+= coordinates[4]*orizontal_velocity; }
		timer++;
		
		for (ConceptModel model : Model.models) {
			if (model.name.startsWith("plat") || model.name.startsWith("bub")) {
				boolean inside_collision= Model.projectileCollision(coordinates[0], coordinates[1], coordinates[2], coordinates[3], model);
				
				if (model.name.startsWith("bub") && inside_collision) {
					coordinates[4]= 0;
					orizontal_velocity= 0;
				}
				else if (model.name.startsWith("water")) {
					String collision= Model.collisionDetection(coordinates[0], coordinates[1], coordinates[2], coordinates[3], model);
					if (collision != null) 
						switch (collision) {
						case "right": coordinates[0]-= 2*Model.scale; break;
						case "left": coordinates[0]+= 2*Model.scale; break;
						case "bottom": coordinates[1]-= Model.scale; break;
						case "up": coordinates[1]+= Model.scale; break;
						}
				}
			}
		}
	}

}
