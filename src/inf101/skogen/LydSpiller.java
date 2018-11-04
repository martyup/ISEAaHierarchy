package inf101.skogen;

import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class LydSpiller {
	private static HashMap<String, Clip> clips = new HashMap<String, Clip>();

	public static String lydSti = "sounds/";

	/**
	 * @param lyd
	 *            Navn på lydfilen som skal spilles av (leter i
	 *            <code>lydSti</code> underkatalogen)
	 */
	public static void spillLyd(String lyd) {
		if (!clips.containsKey(lyd)) {
			try {
				AudioInputStream ais = AudioSystem
						.getAudioInputStream(LydSpiller.class
								.getResource(lydSti + lyd));
				Clip clip = AudioSystem.getClip();
				clip.open(ais);
				clips.put(lyd, clip);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Clip c = clips.get(lyd);

		c.setFramePosition(0);
		c.start();

	}
}
