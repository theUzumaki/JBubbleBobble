package Model;

/**
 * Represents the special powers at a logic level
 * @author Mattia
 * 
 */
public class SpecialPowerModel extends EntityModel{
	
	/**
	 * Indicates status of the special power
	 */
	private boolean ground, water, lightning;
	
	
	/**
	 * Assigns values passed as args to attributes and distinguish between the two kind of powers
	 */
	public SpecialPowerModel(String entityName, int x, int y, int width, int heigth, int verse) {
		super(entityName, x, y, width, heigth);
		verticalVelocity= -Model.scale/3;
		
		coordinates= new int[]{ x, y, width, heigth, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		models.put(entityName, coordinates);
		
		coordinates[4]= verse;
		
		if (entityName.startsWith("spec l")) {
			lightning= true;
			coordinates[5]= 1;
		}
		else {
			water= true;
			coordinates[5]= 2;
			coordinates[7]= 3;
			coordinates[6]= 2;
			coordinates[2]= Model.tile; coordinates[3]= Model.tile;
			coordinates[0]= (coordinates[0] / Model.tile) * Model.tile;
			coordinates[1]= (coordinates[1] / Model.tile) * Model.tile;
			coordinates[4]= verse;
			timer= 0;
		}
	}

	@Override
	/**
	 * Updates the position and status of the object and checks for eventual collisions
	 */
	public void update() {
		if (coordinates[18] > Model.tile*Model.rows) {
			coordinates[6]= 1;
		}
		
		// water segment
		if (water && coordinates[6] < 6 && timer % 8 == 0) { coordinates[6]+= 1; }
		if (water && timer % 8 == 0) {
			if (coordinates[6] > 5) { coordinates[17]= coordinates[14]; coordinates[18]= coordinates[15];
				switch (coordinates[16]) {
				case 0: coordinates[19]= 2; break;
				case 1: coordinates[19]= 3; break;
				case 2: coordinates[19]= 0; break;
				case 3: coordinates[19]= 1; break;
				}
			}
			if (coordinates[6] > 4) { coordinates[14]= coordinates[11]; coordinates[15]= coordinates[12]; coordinates[16]= coordinates[13]; }
			if (coordinates[6] > 3) { coordinates[11]= coordinates[8]; coordinates[12]= coordinates[9]; coordinates[13]= coordinates[10]; }
			if (coordinates[6] > 2) { coordinates[8]= coordinates[0]; coordinates[9]= coordinates[1]; coordinates[10]= coordinates[7]; }
		}
		
		if (water && ground && timer % 8 == 0) coordinates[0]+= coordinates[4]*Model.tile;
		
		if (water && ground && coordinates[7] == 3) coordinates[7]= 1 + coordinates[4];
		else if (water && !ground) coordinates[7]= 3;
		
		// lightning segment
		if (lightning) coordinates[0]+= coordinates[4]*2*Model.scale;
		
		// borders and gravity
		if (coordinates[0] < Model.tile) {
			if (water) { coordinates[0]+= Model.tile; coordinates[7]= 2; coordinates[4]= 1; }
		}
		else if (coordinates[0] > Model.tile*(Model.columns-2)) {
			if (water) { coordinates[0]-= Model.tile; coordinates[7]= 0; coordinates[4]= -1; }
		}
		
		if (water && timer % 8 == 0) coordinates[1]+= Model.tile; 
		else if (!lightning) coordinates[1]-= verticalVelocity; 
		timer++;

		ground= false;
		for (ConceptModel model : Model.models) {
			if (!this.equals(model)) {
				
				if (model.name.startsWith("plat")) {
					if (water && Model.projectileCollision(coordinates[0], coordinates[1], coordinates[2], coordinates[3], model)) {
						ground= true;
						coordinates[1]= (coordinates[1] / Model.tile) * Model.tile-1;
					}
				}
				else if (Model.projectileCollision(coordinates[0], coordinates[1], coordinates[2], coordinates[3], model)) {
					if (model.name.startsWith("bub")) {						

					}
					else if (model.name.startsWith("monsta")) {
						model.removeModel();
						Model.hud.addPoints(6000);
					}
					else if (model.name.startsWith("mighta")) {
						model.removeModel();
						Model.hud.addPoints(8000);
					}
					else if (model.name.startsWith("zen")) {
						model.removeModel();
						Model.hud.addPoints(2000);
					}
				}
			}
		}
	}

}
