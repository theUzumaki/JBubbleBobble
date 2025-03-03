package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JFrame;

import Controller.KeyHandler;
import Model.Model;

@SuppressWarnings({"serial", "deprecation"})
/**
 * Handles all the other Model package classes
 * @author Mattia
 * 
 */
public class View extends JPanel{
	
	/**
	 * Only instance existing as in singleton pattern
	 */
	private static View instance;
	
	/**
	 * Stat passed by the GamePanel giving info about screen size
	 */
	int tile, scale, columns, rows;
	
	/**
	 * Indicates whether or not all sprites are loaded for the current level
	 */
	boolean imagesLoaded= false;
	
	/**
	 * Arrays with all the sub classes of EntityView
	 */
	ArrayList<EntityView> entities= new ArrayList<>();
	
	/**
	 * Arrays with all the sub classes of MaterialView
	 */
	ArrayList<MaterialView> materials= new ArrayList<>();
	
	/**
	 * Arrays with all the sub classes of ConceptView
	 */
	ArrayList<ConceptView> views= new ArrayList<>();
	
	/**
	 * Player instance
	 */
	BubblunView bub;
	
	/**
	 * Stage instance, used to set level
	 */
	StageView stage;
	
	/**
	 * Menu instance, used to monitor level
	 */
	MenuView menu;
	
	/**
	 * Model instance, used to add observers 
	 */
	Model model;
	
	/**
	 * Assign values passed by args and generates HUD, stage, menu and player, other than set window size
	 */
	private View(int tile, int scale, int columns, int rows, KeyHandler keys, Model model) {
		JFrame window= new JFrame();
		
		window.setPreferredSize(new Dimension(columns*tile, rows*tile + tile));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("BubbleBobble");
		window.add(this);
		window.pack();
		window.setLocationRelativeTo(null);
		
		window.setVisible(true);
		
		this.tile= tile;
		this.scale= scale;
		this.columns= columns;
		this.rows= rows;
		this.model= model;
		
		bub= BubblunView.getInstance("bub", this);
		stage= StageView.getInstance("stage", this);
		menu= MenuView.getInstance("menu", this);
		HUDView.getInstance("hud", this);
		new Sound(this);
		
		setPreferredSize(new Dimension(tile*columns, tile*rows));
		setBackground(Color.black);
		setDoubleBuffered(true);
		addKeyListener(keys);
		setFocusable(true);
	}
	
	/**
	 * Only method to get the instance
	 */
	public static View getInstance(int tile, int scale, int columns, int rows, KeyHandler keys, Model model) {
		if (instance==null) {
			instance= new View(tile, scale, columns, rows, keys, model);
		}
		return instance;
	}
	
	/**
	 * Used to add observer to the model list
	 */
	public void addObserver(ConceptView view) {
		model.addObserver(view);
	}
	
	/**
	 * Used to remove observer to the model list
	 */
	public void removeObserver(ConceptView view) {
		model.deleteObserver(view);
	}
	
	/**
	 * Calls the draw of all the objects
	 */
	public void paintComponent(Graphics brush) {
		super.paintComponent(brush);
		ArrayList<ConceptView> viewsArray= this.views;
		
		if (!imagesLoaded) {
			if (!viewsArray.stream().anyMatch(view -> view.imagesLoaded == false)) {
				imagesLoaded= true;
			}
		}
		
		if (imagesLoaded) {
			if (stage.values == null) { }
			else if (stage.values[7] != -1) {			
				for (int i= 0; i < viewsArray.size(); i++) {
					ConceptView view= viewsArray.get(i);
					if (((view.name == "bub" && (stage.values == null || stage.values[6] > 0)) || view.name != "bub") && view.imagesLoaded) view.draw(brush);
				}
			}
			else {
				stage.draw(brush);
			}
		}
		brush.dispose();
	}
}
