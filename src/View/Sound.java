package View;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.sound.sampled.*; 
import java.net.URL;

@SuppressWarnings({"deprecation", "unchecked"})
/**
 * Handles the playing of music and sound effects
 * @author Mattia
 * 
 */
public class Sound implements Observer {
	
	/**
	 * Indicates if music is playing in this moment
	 */
	private boolean musicPlaying;
	
	/**
	 * Array used to load all the sound effects
	 */
	Clip[] clipList= new Clip[5];
	
	/**
	 * Used to load the music to play
	 */
	Clip clip;
	
	/**
	 * Array with all the musics
	 */
	URL musicURL[];
	
	/**
	 * Array with all the sound effects
	 */
	URL soundURL[];
	
	/**
	 * Used to add this object to the list of observers to notify
	 */
	View view;

	/**
	 * Adds the object to the list of observers
	 */
	public Sound(View view) {
		this.view= view;
		view.model.addObserver(this);
		
		loadSounds();
	}

	@Override
	/**
	 * Retrieves the values sent by the observable
	 */
	public void update(Observable o, Object arg) {
		int[] values= ((Map<String, int[]>)arg).get("sound");
		
		if (values[0] != 0) {
			stopMusic();
			musicPlaying= false;
		}
		if (values[1] > 0) {
			if (!musicPlaying) playMusic(values[1]-1);
			musicPlaying= true;
		}
		
		if (values[2] > 0 ) {
			playSE(values[2]-1);
		}
	}

	/**
	 * Loads all the sounds inside their array
	 */
	private void loadSounds() {
		soundURL= new URL[] {
				getClass().getResource("/sounds/win.wav"),
				getClass().getResource("/sounds/lose.wav"),
				getClass().getResource("/sounds/jump.wav"),
				getClass().getResource("/sounds/power up.wav"),
				getClass().getResource("/sounds/bubble.wav"),
			};
		
		musicURL= new URL[] {
				getClass().getResource("/sounds/intro.wav"),
				getClass().getResource("/sounds/gameplay.wav"),
			};
	}
	
	/**
	 * Loads the music inside a clip and plays it continuously
	 */
	private void playMusic(int i) {
		try {
			clip= AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(musicURL[i]));
		} catch(Exception e) {
			e.printStackTrace();
		}
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * Loads the sound inside a clip and plays it continuously
	 */
	private void playSE(int i) {
		try {
			clipList[i]= AudioSystem.getClip();
			clipList[i].open(AudioSystem.getAudioInputStream(soundURL[i]));
		} catch(Exception e) {
			e.printStackTrace();
		}
		clipList[i].start();
	}
	
	/**
	 * Stops the music clip
	 */
	private void stopMusic() {
		clip.stop();
	}
}
