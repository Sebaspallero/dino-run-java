package game.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.entities.character.Dinosaur;
import game.main.GamePanel;
import game.utils.SoundPlayer;

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
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        switch (gamePanel.getGameState()) {
            case TITLE:
                gamePanel.setGameState(GamePanel.GameState.PLAYING);
                break;

            case PLAYING:
                if (dinosaur.getCurrentState() != Dinosaur.State.HIT) {
                    dinosaur.jump();
                    soundPlayer.play();
                }
                break;

            default:
                break;
        }
    }

    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        if (gamePanel.isGameOver()) {
            gamePanel.resetGame();
        }
    }

    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        if (gamePanel.getGameState() == GamePanel.GameState.PLAYING &&
            dinosaur.getCurrentState() != Dinosaur.State.HIT) {
            dinosaur.crouch();
        }
    }
}


    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (dinosaur.getCurrentState() != Dinosaur.State.HIT) {
                dinosaur.stand();
            }
        }
    }

    public void setDinosaur(Dinosaur dinosaur) {
        this.dinosaur = dinosaur;
    }

}
