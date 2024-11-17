package game.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class SoundPlayer {

    private Clip clip;
    private final URL soundUrl[] = new URL[30];

    public SoundPlayer(){
        soundUrl[0] = getClass().getResource("/resources/sfx/game-jump.wav");
        soundUrl[1] = getClass().getResource("/resources/sfx/game-start.wav");
        soundUrl[2] = getClass().getResource("/resources/sfx/game-over.wav");
    }

    public void setFile(int index){
        if (index < 0 || index > soundUrl.length || soundUrl[index] == null) {
            System.err.println("Error: Invalid index sound or file not found");
        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl[index]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error: Could not upload the sound file " + e.getMessage());
        }
    }
    
    public void play() {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0); 
            clip.start();        
        }
    }

    public void playAndWait() {
        if (clip != null) {
            clip.start();
            try {
                while (!clip.isRunning()) {
                    Thread.sleep(10);
                }
                while (clip.isRunning()) {
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void loop(){
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop(){
        clip.stop();
        clip.close(); 
    }
}
