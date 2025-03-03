package Controller;

import javax.swing.JPanel;

import Model.Model;
import View.View;

@SuppressWarnings("serial")
/**
 * Manages the game thread
 * @author Mattia
 *
 */
public class GamePanel extends JPanel implements Runnable{
	/**
	 * Dimension of tile
	 */
	final int og_tile= 8;
	/**
	 * Scale of screen output
	 */
	final int scale= 3;
	/**
	 * Dimension of tile multiplied for scale
	 */
	final int tile= og_tile*scale;
	/**
	 * Number of columns
	 */
	final int columns= 32;
	/**
	 * Number of rows
	 */
	final int rows= 28;
	/**
	 * Thread of game flow
	 */
	Thread gameThread;
	/**
	 * Manages keys input
	 */
	KeyHandler keys;
	/**
	 * Manages class of model package
	 */
	Model model;
	/**
	 * Manages class of view package
	 */
	View view;

	/**
	 * Creates model, view and keys object
	 */
	public GamePanel() {
		keys= new KeyHandler();
		model= Model.getInstance(tile, scale, columns, rows);
		view= View.getInstance(tile, scale, columns, rows, keys, model);
	}
	/**
	 * Starts game thread
	 */
	public void startGame() {
		gameThread= new Thread(this);
		gameThread.start();
	}
	@Override
	/**
	 * Iterates till game thread is not null
	 */
	public void run() {
		double draw_interval= 1000000000/60, delta= 0;
		long last_time= System.nanoTime(), current_time;
		
		while (gameThread != null) {
			current_time= System.nanoTime();
			delta+= (current_time - last_time) / draw_interval;
			last_time= current_time;
			
			if (delta>= 1) {
				model.update(keys.keys, keys.jump, keys.enter, keys.exit);
				view.repaint();
				delta--;
			}
		}
	}
}
