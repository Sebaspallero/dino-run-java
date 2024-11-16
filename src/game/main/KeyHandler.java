package game.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.entities.Dinosaur;
import game.sound.SoundPlayer;

public class KeyHandler implements KeyListener{

    private Dinosaur dinosaur;
    private SoundPlayer soundPlayer;

    public KeyHandler(Dinosaur dinosaur){
        this.dinosaur = dinosaur;
        this.soundPlayer = new SoundPlayer();
        soundPlayer.setFile(0);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) {
            dinosaur.jump();
            soundPlayer.play();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
