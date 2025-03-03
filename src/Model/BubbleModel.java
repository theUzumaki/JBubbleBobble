package Model;

/**
 * Represents the bubbles at a logic level
 * @author Mattia
 * 
 */
public class BubbleModel extends EntityModel{
	
	/**
	 * Indicates status of the bubble
	 */
	private boolean ground= true, jump, jumping, fruit, stopped, special, water;
	
	/**
	 * Indicates velocity of the bubble
	 */
	private int orizontalVelocity;
	
	/**
	 * Measures frame passing
	 */
	private int distanceTimer, deathTimer;

	/**
	 * Assigns values passed as args to attributes
	 */
	public BubbleModel(String entityName, int x, int y, int width, int heigth, int verse, int orizontalVelocity, int distanceTimer, boolean special) {
		super(entityName, x, y, width, heigth);
		coordinates[4]= verse;
		verticalVelocity= -Model.scale/2;
		this.orizontalVelocity= orizontalVelocity;
		this.distanceTimer= distanceTimer;
		this.special= special;
		if (special) stopped= true;
		
		if (name.startsWith("bubble l")) coordinates[5]= -11;
		else if (name.startsWith("bubble w")) { coordinates[5]= -12; water= true; }
	}

	@Override
	/**
	 * Updates the position and status of the object and eventual collisions
	 */
	public void update() {
		
		if (coordinates[1] < 0 && coordinates[5] < 4) {
			coordinates[1]= Model.tile*Model.rows;
		}
		
		if (deathTimer >= 29) {
			if (special && !water) name= "spec lightning"+name.toCharArray()[name.length()-1];
			else if (special) name= "spec water"+name.toCharArray()[name.length()-1];
			if (special) new SpecialPowerModel(name, coordinates[0], coordinates[1], coordinates[2], coordinates[3], coordinates[4]);
			coordinates[6]= 1;
		}
		if (!jumping && !stopped) coordinates[0]+= coordinates[4]*orizontalVelocity;
		else if (special && ground) coordinates[0]+= coordinates[4]*Model.scale/2;
		if (jump) {
			verticalVelocity= Model.scale*4;
			jumping= true;
			jump= false;
		}
		if (jumping && verticalVelocity > -Model.scale*5 && timer%5 == 0) { verticalVelocity-= Model.scale; }
		else if (!jumping || verticalVelocity <= -Model.scale*5) { verticalVelocity= -Model.scale/2; }
		
		if (!stopped) {
			if (timer == distanceTimer/3) { coordinates[2]= 6*Model.tile/4; coordinates[3]= 6*Model.tile/4; }
			else if (timer == distanceTimer/2) { coordinates[2]= 7*Model.tile/4; coordinates[3]= 7*Model.tile/4; }
			else if (timer == distanceTimer) {
				coordinates[2]= 2*Model.tile; coordinates[3]= 2*Model.tile;
				coordinates[4]= -coordinates[4]; stopped= true;
				orizontalVelocity= Model.scale/3;
			}
		}
		
		if (coordinates[0] < Model.tile) { coordinates[0]+= Model.scale; coordinates[4]= 1; }
		else if (coordinates[0] > Model.tile*(Model.columns-3)) { coordinates[0]-= Model.scale; coordinates[4]= -1; }
		if (special || jumping || fruit) coordinates[1]-= verticalVelocity;
		else if (stopped) coordinates[1]+= verticalVelocity;
		if (coordinates[5] == -1) deathTimer++;
		else if (coordinates[5] > 9) deathTimer+= 2;
		timer++;
		
		ground= false;
		for (ConceptModel model : Model.models) {
			if (!this.equals(model)) {
				boolean insideCollision= Model.projectileCollision(coordinates[0], coordinates[1], coordinates[2], coordinates[3], model);
				String collision= Model.collisionDetection(coordinates[0], coordinates[1], coordinates[2], coordinates[3], model);
				
				if (verticalVelocity < 0 && model.name.startsWith("plat")) {
					if (!jumping && stopped && collision == "top") {
						coordinates[0]+= coordinates[4]*orizontalVelocity;
						coordinates[1]-= verticalVelocity;
					}
					else if (coordinates[1] > Model.tile*3 && (special || jumping || fruit) && collision == "bottom") {
						ground= true;
						coordinates[1]+= verticalVelocity;
						if (jumping && coordinates[5] <= 6) { jumping= false; fruit= true; coordinates[5]+= 3; orizontalVelocity= 0; }
					}
				}
				else if (model.name.startsWith("bubble") && model.coordinates[5] < 4) {
					if (collision == "top") {
						coordinates[1]+= verticalVelocity;
					}
					else if (collision == "left") {
						coordinates[0]+= orizontalVelocity;
					}
					else if (collision == "right") {
						coordinates[0]-= orizontalVelocity;
					}
				}
				else if (special) {
					if (model.name.startsWith("bub")) {
						if (collision != null) {
							switch (collision) {
							case "left": coordinates[4]= -1; break;
							case "right": coordinates[4]= 1; break;
							case "top": coordinates[4]= -1; break;
							case "bottom": coordinates[4]= 1; break;
							}
							coordinates[5]= -1;
							orizontalVelocity= 0;
						}
					}
				}
				else if (coordinates[5] == 0 && insideCollision) {
					if (model.name.startsWith("zen")) {						
						model.removeModel();
						coordinates[5]= 1;
						orizontalVelocity= Model.scale/3;
					}
					else if (model.name.startsWith("monsta")) {
						model.removeModel();
						coordinates[5]= 2;
						orizontalVelocity= Model.scale/3;
					}
					else if (model.name.startsWith("mighta")) {
						model.removeModel();
						coordinates[5]= 3;
						orizontalVelocity= Model.scale/3;
					}
					else if (model.name.startsWith("bub")) {
						if (collision == "bottom" && coordinates[5] == 0) {
							coordinates[5]= -1;
							orizontalVelocity= 0;
						}
						else if (collision != null) {
							if (collision == "top") {
								coordinates[1]-= verticalVelocity;
							}
							else if (collision == "left") {
								coordinates[0]+= orizontalVelocity;
							}
							else if (collision == "right") {
								coordinates[0]-= orizontalVelocity;
							}
						}
					}
				}
				else if (model.name.startsWith("bub")) {
					if (!special && !jumping && insideCollision && coordinates[5] >= 1 && coordinates[5] <= 9) {
						coordinates[5]+= 3;
						jump= true;
					}
				}
			}
		}
	}

}
