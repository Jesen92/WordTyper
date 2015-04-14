import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Sound {
	
	public static File type = new File("/typewriter.wav");
	public static File lifeLost = new File("/life_lost.wav");
	public static File ding = new File("/type_ding.wav");
	public static File points_100 = new File("/100_points.wav"); 
	
	public Sound(){}
	
	public static synchronized void playSound(File file) {

	      try {
	        Clip clip = AudioSystem.getClip();
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
	        clip.open(inputStream);
	        clip.start(); 
	      } catch (Exception e) {
	        System.err.println(e.getMessage());
	      }		        
}
}
