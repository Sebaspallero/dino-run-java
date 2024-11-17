package game.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.entities.Dinosaur;
import game.sound.SoundPlayer;

public class KeyHandler implements KeyListener{

    private Dinosaur dinosaur;
    private SoundPlayer soundPlayer;
    private GamePanel gamePanel;

    public KeyHandler(Dinosaur dinosaur, GamePanel gamePanel){
        this.dinosaur = dinosaur;
        this.gamePanel = gamePanel;
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
            if (gamePanel.isGameOver()) {
                gamePanel.resetGame();
            }else{
                dinosaur.jump();
                soundPlayer.play();
            }  
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void setDinosaur(Dinosaur dinosaur) {
        this.dinosaur = dinosaur;
    }

}
