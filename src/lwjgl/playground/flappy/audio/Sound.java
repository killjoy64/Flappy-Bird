package lwjgl.playground.flappy.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Created by kylef_000 on 9/2/2016.
 */
public class Sound {

    public static Sound loadSound(String fileName) {
        Sound sound = new Sound();
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            sound.clip = clip;
        } catch (Exception e) {
            System.out.println(e);
        }
        return sound;
    }

    private Clip clip;

    public void play() {
        try {
            if (clip != null) {
                new Thread() {
                    public void run() {
                        synchronized (clip) {
                            clip.stop();
                            clip.setFramePosition(0);
                            clip.start();
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
