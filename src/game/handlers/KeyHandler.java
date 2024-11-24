package game.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.entities.character.Character;
import game.main.GamePanel;
import game.utils.SoundPlayer;

public class KeyHandler implements KeyListener {

    private Character character;
    private SoundPlayer soundPlayer;
    private GamePanel gamePanel;

    public KeyHandler(Character character, GamePanel gamePanel) {
        this.character = character;
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
                    if (character.getCurrentState() != Character.State.HIT) {
                        character.jump();
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
                    character.getCurrentState() != Character.State.HIT) {
                character.crouch();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (character.getCurrentState() != Character.State.HIT) {
                character.stand();
            }
        }
    }

    public void setcharacter(Character character) {
        this.character = character;
    }

}
