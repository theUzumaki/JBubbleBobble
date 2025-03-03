package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;

/**
 * Represents the menu at a logic level
 * @author Mattia 
 * 
 */
public class MenuModel extends ConceptModel{
	
	/**
	 * Measures frame passing
	 */
	private int timer;
	
	/**
	 * Stat of logged player
	 */
	private int wins, losses;
	
	/**
	 * Stat of logged player
	 */
	int highScore;
	
	/**
	 * Used to regulate menu update
	 */
	private boolean intro, password;
	
	/**
	 * Only instance existing as in singleton pattern
	 */
	private static MenuModel instance;
	
	/**
	 * Name inserted at login
	 */
	String playerName;
	
	/**
	 * Array of lines in the leaderboard file
	 */
	String leaderboard[]= new String[5];
	
	/**
	 * Assigns values passed as args to attributes
	 */
	private MenuModel(String materialName) {
		super();

		name= materialName;
		coordinates= new int[]{ 13, 15, 0, 0, -3, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		models.put(materialName, coordinates);
		newScore("0");
	}
	
	/**
	 * Only method to get the instance
	 */
	static public MenuModel getInstance(String materialName) {
		if (instance == null) { instance= new MenuModel(materialName); }
		return instance;
	}

	/**
	 * Updates the values of the object based on user input
	 */
	public void update(boolean exit, boolean enter,
			boolean up, boolean down, boolean left, boolean right, boolean leaderBoard) {
		int newLevel= Model.stage.newLevel;
		int oldLevel= Model.stage.oldLevel;
			
		if (newLevel == -1) {
			coordinates[11]= wins;
			coordinates[12]= losses;
			
			if (coordinates[0] > 7 && coordinates[1] != 22) {
				coordinates[0]= 7; coordinates[1]= 7; 
			}

			if (coordinates[6] >= 10000000) {				
				coordinates[6]= 0;
			}
			coordinates[4]= oldLevel;
			if (up && timer > 12) {
				if (coordinates[1] > 7 && coordinates[0] == 7) { coordinates[1]-= 6; timer= 0; }
				else if (coordinates[1] == 22) {
					switch (coordinates[0]) {
					case 9: coordinates[6]+= 1; break;
					case 11: coordinates[7]+= 1; break;
					case 13: coordinates[8]+= 1; break;
					case 15: coordinates[9]+= 1; break;
					case 17: coordinates[10]+= 1; break;
					}
				}
				timer= 0;
			}
			else if (down && timer > 12) {
				if (coordinates[1] < 19 && coordinates[0] == 7) { coordinates[1]+= 6; timer= 0; }
				else if (coordinates[1] == 22) {
					switch (coordinates[0]) {
					case 9: coordinates[6]-= 1; break;
					case 11: coordinates[7]-= 1; break;
					case 13: coordinates[8]-= 1; break;
					case 15: coordinates[9]-= 1; break;
					case 17: coordinates[10]-= 1; break;
					}
				}
				timer= 0;
			}
			else if (right && timer > 12) {
				if (coordinates[1] == 22 && coordinates[0] < 17) { coordinates[0]+= 2; }
				timer= 0;
			}
			else if (left && timer > 12) {
				if (coordinates[1] == 22 && coordinates[0] > 9) { coordinates[0]-= 2; }
				timer= 0;
			}
			else if (enter && timer > 12) {
				if (coordinates[1] == 7) {
					models.get("sound")[0]= 1;
					models.get("sound")[1]= 2;
					Model.stage.setLevel(1);
					coordinates[2]= 1;
					if (Model.bub.coordinates[6] == -1) {						
						coordinates[5]= 1;
						Model.bub.respawn();
					}
					intro= true;
				}
				else if (coordinates[1] == 19) {
					coordinates[0]= 9;
					coordinates[1]= 22;
					coordinates[5]= -2;
				}
				else if (coordinates[1] == 22) {
					if (coordinates[6] <= 9) {
						coordinates[5]= 0;
						models.get("sound")[0]= 1;
						models.get("sound")[1]= 2;
						Model.stage.setLevel(coordinates[6]+1);
						if (Model.bub.coordinates[6] == -1) {						
							coordinates[5]= 1;
							coordinates[2]= 1;
							Model.bub.respawn();
						}
						intro= true;
						int score= coordinates[10] + coordinates[9]*26 +
								coordinates[8]*26*26 + coordinates[7]*26*26*26;
						Model.hud.addPoints(score);
					}
				}
				timer= 0;
			}
			else if (exit && timer > 12) {
				if (coordinates[1] == 22) {
					coordinates[0]= 7;
					coordinates[1]= 19;
				}
			}
			else if (leaderBoard) {
				Model.stage.setLevel(-4);
				coordinates[4]= -4;
				coordinates[5]= 0;
			}
		}
		else if (newLevel == -3) {
			if (up && timer > 12) {
				switch (coordinates[0]) {
				case 13: coordinates[7]+= 1; if (coordinates[7] == 26) { coordinates[7]= 1; }; break;
				case 15: coordinates[8]+= 1; if (coordinates[8] == 26) { coordinates[8]= 1; }; break;
				case 17: coordinates[9]+= 1; if (coordinates[9] == 26) { coordinates[9]= 1; }; break;
				}
				
				timer= 0;
			}
			else if (down && timer > 12) {
				switch (coordinates[0]) {
				case 13: coordinates[7]-= 1; if (coordinates[7] == -1) { coordinates[7]= 25; }; break;
				case 15: coordinates[8]-= 1; if (coordinates[8] == -1) { coordinates[8]= 25; }; break;
				case 17: coordinates[9]-= 1; if (coordinates[9] == -1) { coordinates[9]= 25; }; break;
				}
				timer= 0;
			}
			else if (right && timer > 12) {
				if (coordinates[0] < 17) coordinates[0]+= 2;
				timer= 0;
			}
			else if (left && timer > 12) {
				if (coordinates[0] > 13) coordinates[0]-= 2;
				timer= 0;
			}
			else if (enter) {				
				playerName= ""+((char)(coordinates[7]+65)) + ((char)(coordinates[8]+65)) + ((char)(coordinates[9]+65));
				coordinates[7]= 0;
				coordinates[8]= 0;
				coordinates[9]= 0;
				newSave(playerName);

// TO CHECK
				Model.stage.setLevel(0);
				coordinates[4]= 0;
			}
		}
		else if (newLevel == -4) {
			int i= 7;
			if (coordinates[21] == 0) {				
				for (String line : leaderboard) {
					coordinates[i]= line.substring(0,1).toCharArray()[0];
					coordinates[i+1]= line.substring(1,2).toCharArray()[0];
					coordinates[i+2]= line.substring(2,3).toCharArray()[0];
					coordinates[i+3]= Integer.parseInt(line.substring(4));
					
					coordinates[i]= coordinates[i]-65;
					coordinates[i+1]= coordinates[i+1]-65;
					coordinates[i+2]= coordinates[i+2]-65;
					coordinates[i+3]= coordinates[i+3]-65;
					i+= 4;
				}
			}
			if (exit) {
				Model.stage.setLevel(-1);
				coordinates[4]= -1;
				coordinates[21]= 0;
			}
		}
		else if (intro && newLevel > 0) {
			coordinates[4]= newLevel;
			coordinates[5]= 0;
			if (timer > 120) { intro= false; coordinates[4]= -10; }
		}
		else if (newLevel > 0 && Model.bub.coordinates[6] != -1) {
			coordinates[2]= 0;
		}
		else if (newLevel == -2) {
			coordinates[4]= newLevel;
			if (!password) {
				coordinates[6]= 10000000*oldLevel;
				coordinates[6]+= Model.hud.coordinates[5];
				coordinates[2]= 2;
				password= true;
			}
			if (enter) {
				coordinates[2]= 1;
				Model.hud.loadOldPoints();
				Model.stage.setOldLevel();
				coordinates[4]= Model.stage.newLevel;
				coordinates[5]= Model.stage.newLevel;
				Model.bub.respawn();
				password= false;
				intro= true;
				models.get("sound")[1]= 2;
				timer= 0;
			}
			else if (exit) {
				coordinates[2]= 0;
				Model.stage.setLevel(-1);
				Model.hud.resetPoints();
				models.get("sound")[1]= 1;
				password= false;
			}
		}
		else {
		}
		
		timer++;
	}
	
	/**
	 * Sets the values to play the intro
	 */
	public void newLevel() {
		intro= true;
		timer= 0;
	}
	
	/**
	 * It is called everytime a game is concluded and updates the save of the player
	 */
	public void saveUpdate(int newScore, boolean win) {
		
		File saves= new File("saves.txt");
		StringBuffer stringBuffer= new StringBuffer();
		String line;
		
		String toFind= playerName+"|"+wins+"|"+losses+"|"+highScore;
		
		try {
			Scanner reader = new Scanner(saves);
		      while (reader.hasNextLine() && (line= reader.nextLine()) != null) {
		    	  stringBuffer.append(line+"\n");
		      }
		      reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int startIndex = stringBuffer.indexOf(toFind);
		int endIndex = startIndex + toFind.length();
		
		if (newScore > highScore) highScore= newScore;
		
		if (win) wins++;
		else losses++;
		
		String toReplace= playerName+"|"+wins+"|"+losses+"|"+highScore;
		stringBuffer.replace(startIndex, endIndex, toReplace);
		
		try {
            PrintWriter bufwriter = new PrintWriter(new FileWriter("saves.txt"));
            bufwriter.write(stringBuffer.toString());
            bufwriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * It is called everytime a player terminates a complete game updating the leaderboard
	 */
	public void newScore(String score) {
		File leaderboard= new File ("leaderboard.txt");
		String[] scores= new String[6];
		String line;
		
		try {
			leaderboard.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Scanner reader = new Scanner(leaderboard);
			int i= 0;
		    while (reader.hasNextLine()) {
		    	line= reader.nextLine();
		    	if (line == null) break;
		    	scores[i]= line;
		    	i++;
		    }
		    reader.close();
		    scores[5]= playerName+score;
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		int j= 1;
		for (int k= 0; k < scores.length; k++) {
			int y= k;
			String value1= scores[k];
			if (value1.equals("null") || (value1.compareTo("") == 0)) {
			}
			else {
				for (int i= j; i < scores.length; i++) {
					String value2= scores[i];
					if (value2.equals("null") || value2.equals("")) {}
					else if (Integer.parseInt(value1.substring(4)) < Integer.parseInt(value2.substring(4))) {
						y= i;
						value1= scores[y];
					}
				}
			}
			if (y != k) {				
				String limbo= scores[y];
				scores[y]= scores[k];
				scores[k]= limbo;
			}
			j++;
		}
		scores[5]= null;
		
		int i= 0;
		for (String x : scores) {
			if (i<5) {				
				this.leaderboard[i]= x;
			}
			i++;
		}
		
		try {					
			PrintWriter writer = new PrintWriter(new FileWriter("leaderboard.txt", false));
			for (String lineOfFile : scores) {				
				writer.println(lineOfFile);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void newSave(String name) {
		boolean found= false;
		int index= 4;
		File saves= new File ("saves.txt");
		
		try {
			saves.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Scanner reader = new Scanner(saves);
		      while (reader.hasNextLine()) {
		        String data = reader.nextLine();
		        if (data.substring(0,3).equals(name)) {
		        	found= true;
		        	String number= "";
		        	while (true) {
		        		if (!data.substring(index, index+1).equals("|")) { number+= data.substring(index, index+1); index++; }
		        		else break;
		        	}
		        	wins= Integer.parseInt(number);
		        	number= "";
		        	index++;
		        	while (true) {
		        		if (!data.substring(index, index+1).equals("|")) { number+= data.substring(index, index+1); index++; }
		        		else break;
		        	}
		        	losses= Integer.parseInt(number);
		        	index++;
		        	number= data.substring(index, data.length());
		        	highScore= Integer.parseInt(number);
		        	break;
		        }
		      }
		      reader.close();
		      Model.hud.setHighScore(highScore);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!found) {			
			try {					
				PrintWriter writer = new PrintWriter(new FileWriter("saves.txt", true));
				writer.println(name+"|0|0|0");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
