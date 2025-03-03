package Model;

/**
 * Represents the zen-chan at a logic level
 * @author Mattia
 * 
 * 
 */
public class ZenModel extends EntityModel {
	
	/**
	 * Indicates status of the zen-chan
	 */
	private boolean ground= true, jump= false, jumping= false, flying= false;

	/**
	 * Assigns values passed as args to attributes
	 */
	public ZenModel(String entity_name, int x, int y, int width, int heigth) {
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
		
		if (!flying) {			
			switch (coordinates[4]) {
			case -1: coordinates[0]-= Model.scale; break;
			case 1: coordinates[0]+= Model.scale; break;
			}
		}
		
		if (ground) { flying= false; }
		if (jump && ground) {
			verticalVelocity= Model.scale*4;
			jump= false;
			jumping= true;
		}
		if (jumping && verticalVelocity > -Model.scale*5 && (timer%5 == 0)) { verticalVelocity-= Model.scale; }
		else if (!jumping && !flying) { verticalVelocity= -Model.scale; }
		
		if (coordinates[0] < Model.tile) {
			coordinates[0]+= Model.scale;
			coordinates[4]= -coordinates[4]; }
		else if (coordinates[0] > Model.tile*(Model.columns-3)) {
			coordinates[0]-= Model.scale;
			coordinates[4]= -coordinates[4]; }
		if (ground && Math.floor(Math.random() * 300) > 298 && coordinates[1] > Model.tile*10) {
			flying= true; verticalVelocity= Model.scale;
			coordinates[1]-= 3*Model.scale;
		}
		coordinates[1]-= verticalVelocity;
		timer++;
		
		String collision;
		ground= false;
		jump= true;
		for (ConceptModel model : Model.models) {
			if (model.name.startsWith("plat")) {
				int times= 0;
				while (times <= Math.abs(verticalVelocity)/Model.scale && (verticalVelocity < 0 || flying) && !model.name.startsWith("bubble")) {
					if (Model.collisionDetection(coordinates[0], coordinates[1]-(Model.scale*times), width, heigth, model) == "bottom") {
						coordinates[1]= Model.tile*(28 - ((Model.tile*28-(coordinates[1]-(Model.scale*times)))/Model.tile + 1));
						jumping= false;
						ground= true;
						break;
					}
					times++;
				}
				collision= Model.collisionDetection(coordinates[0], coordinates[1], width, heigth, model);
				if (!model.name.startsWith("bubble") && collision != "bottom" && collision != null && verticalVelocity < 0) {
					coordinates[4]= -coordinates[4];
				}
				else if (Model.collisionDetection(coordinates[0] + (coordinates[4])*width, coordinates[1] + heigth, width, heigth, model) != null) {
					jump= false;
				}
			}
		}
		
		ConceptModel.models.replace(name, coordinates);
	}

}
