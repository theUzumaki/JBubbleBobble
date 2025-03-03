package Model;

/**
 * Represents the bubblun at a logic level
 * @author Mattia
 * 
 */
public class BubblunModel extends ConceptModel{
	/**
	 * Velocity of player
	 */
	private int verticalVelocity= -Model.scale, plVel= Model.scale;
	/**
	 * Bubble stat
	 */
	private int bubbleVel= 3*Model.scale/2, bubbleDist= 45, bubbleFreq= 30;
	/**
	 * Measures frame passing
	 */
	private int timer= 0, invulnerabilityTimer, bubbleTimer= 120;
	/**
	 * Indicates status of the bubble
	 */
	boolean jumping= false, ground= true, start= true, falling,
			walkPoint, jumpPoint, bubblePoint, invulnerability, dead;
	/**
	 * Only instance existing as in singleton pattern
	 */
	private static BubblunModel instance;

	/**
	 * Assigns values passed as args to attributes
	 */
	private BubblunModel(String entityName, int x, int y, int width, int heigth) {
		super();
		this.width= width;
		this.heigth= heigth;
		name= entityName;
		coordinates= new int[]{ x, y, width, heigth, 0, 0, 0 };
		models.put(name, coordinates);
	}
	
	/**
	 * Only method to get the instance
	 */
	static public BubblunModel getInstance(String entityName, int x, int y, int width, int heigth) {
		if (instance == null) { instance= new BubblunModel(entityName, x, y, width, heigth); }
		return instance;
	}
	
	/**
	 * Updates the position and status of the object and eventual collisions
	 */
	public void update(boolean exit, boolean right, boolean left, boolean jump, boolean bubble) {
		models.get("sound")[2]= 0;
		
		if (!dead) {
		if (coordinates[1] > Model.tile*Model.rows) { coordinates[1]= 0; }
		if (exit) { coordinates[6]= -1; }
		
		if (invulnerability && invulnerabilityTimer > 180) {
			invulnerability= false;
		}
		if (start) {
			coordinates[4]= 6;
			coordinates[0]-= 2*plVel/3; coordinates[1]+= 2*plVel/3;
			if (timer > 100) { start= false; falling= true; coordinates[0]-= Model.tile; coordinates[1]-= Model.tile; }
		}
		if (falling) {
			coordinates[4]= -6;
			if (timer > 164) { falling= false; coordinates[4]= 1; coordinates[0]+= Model.tile; coordinates[1]+= Model.tile; }
		}
		else if (!start) {
			if (left) { coordinates[0]-= plVel; if (!jumping) { coordinates[4]= -1; } else { coordinates[4]= -3; } }
			if (right) { coordinates[0]+= plVel; if (!jumping) { coordinates[4]= 1; } else { coordinates[4]= 3; } }
			if (jump && ground && (timer > 10)) {
				models.get("sound")[2]= 3;
				verticalVelocity= Model.scale*5;
				timer= 0; jumping= true; ground= false;
				if (jumpPoint) { Model.hud.addPoints(100); }
				if (coordinates[4] >= 0) { coordinates[4]= 3; }
				else { coordinates[4]= -3; }
			}
			if (bubble && bubbleTimer > bubbleFreq) {
				models.get("sound")[2]= 4;
				int verse= Math.abs(coordinates[4])/coordinates[4];
				coordinates[4]= verse*5;
				coordinates[5]+= 1;
				bubbleTimer= 0;
				new BubbleModel("bubble"+coordinates[5], coordinates[0] + coordinates[2]*verse, coordinates[1],
						Model.tile, Model.tile, verse, bubbleVel, bubbleDist, false);
			}
			if (timer%5 == 0 && walkPoint && (left || right)) { Model.hud.addPoints(10); }
			if (!left && !right && !jump && !bubble && !jumping) {
				if (coordinates[4] >= 0) { coordinates[4]= 2; }
				else { coordinates[4]= -2; }
			}
			if (!bubble && !jumping && !ground) {
				if (coordinates[4] >= 0) { coordinates[4]= 4; }
				else { coordinates[4]= -4; }
			}
			
			if (jumping && verticalVelocity > -Model.scale*5 && (timer%5 == 0)) { verticalVelocity-= Model.scale; }
			else if (!jumping) { verticalVelocity= -Model.scale; }
			
			if (coordinates[0] < Model.tile) { coordinates[0]+= plVel; }
			else if (coordinates[0] > Model.tile*(Model.columns-3)) { coordinates[0]-= plVel; }
			coordinates[1]-= verticalVelocity;
			
			String collision;
			ground= false;
			for (ConceptModel model : Model.models) {
				if (!this.equals(model)) {
					int times= 0;
					while (times <= Math.abs(verticalVelocity)/Model.scale && verticalVelocity < 0 && model.name.startsWith("plat")) {
						if (Model.collisionDetection(coordinates[0], coordinates[1]-(Model.scale*times), width, heigth, model) == "bottom") {
							coordinates[1]= Model.tile*(28 - ((Model.tile*28-(coordinates[1]-(Model.scale*times)))/Model.tile + 1));
							jumping= false;
							ground= true;
							break;
						}
						times++;
					}
					
					collision= Model.collisionDetection(coordinates[0], coordinates[1], width, heigth, model);
					if (collision != null && verticalVelocity < 0) {
						if (model.name.startsWith("plat")) {
							switch (Math.abs(coordinates[4])/coordinates[4]) {
							case -1: coordinates[0]+= Model.scale; break;
							case 1: coordinates[0]-= Model.scale; break;
							}
						}
					}
					boolean projectile= Model.projectileCollision(coordinates[0], coordinates[1], width, heigth, model);
					if (projectile) {					
						if (model.name.startsWith("power")) {
							models.get("sound")[2]= 3;
							model.coordinates[6]= -1;
							switch (model.coordinates[4]) {
							case 0: bubbleVel= 5*Model.scale/2; break;
							case 1: bubbleDist= 60; break;
							case 2: bubbleFreq= 10; break;
							case 3: plVel= 2*Model.scale; break;
							case 4: walkPoint= true; break;
							case 5: jumpPoint= true; break;
							case 6: bubblePoint= true; break;
							case 7: walkPoint= true; jumpPoint= true; bubblePoint= true; break;
							case 8: walkPoint= true; jumpPoint= true; bubblePoint= true;
								bubbleVel= 5*Model.scale/2; bubbleDist= 60; bubbleFreq= 10; break;
							case 9: bubbleVel= 5*Model.scale/2; bubbleDist= 60; bubbleFreq= 10; break;
							}
						}
						else if (!model.name.startsWith("plat") && !model.name.startsWith("bubble") &&
								!model.name.startsWith("spec")) {
							if (!invulnerability && coordinates[6] > 0) {
								coordinates[6]--;
								invulnerability= true;
								invulnerabilityTimer= 0;
							}
							else if (!invulnerability){
								models.get("sound")[0]= 1;
								models.get("sound")[2]= 2;
								timer= 0;
								dead= true;
							}
						}
					}
				}
			}			
		}
		}
		else if (timer > 135){
			coordinates[6]= -1;
		}
		invulnerabilityTimer++; timer++; bubbleTimer++;
	
		ConceptModel.models.replace(name, coordinates);
	}
	
	/**
	 * sets back the lives of the player, resets its position and starts the intro
	 */
	public void respawn() {
		if (coordinates[6] == -1) {
			coordinates[6]= 0;
			coordinates[0]= 11*Model.tile;
			coordinates[1]= (Model.rows-13)*Model.tile;
			start= true;
			bubbleTimer= 120; bubbleVel= 3*Model.scale/2; bubbleDist= 45; bubbleFreq= 30;
			walkPoint= false; jumpPoint= false; bubblePoint= false;
			plVel= Model.scale;
		}
		else {
			coordinates[0]= 11*Model.tile;
			coordinates[1]= (Model.rows-13)*Model.tile;
			start= true;
		}
		dead= false;
		timer= 0;
	}
}
