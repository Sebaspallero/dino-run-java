package game.main;

import java.awt.*;

import game.entities.Dinosaur;
import game.sound.SoundPlayer;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    private Dinosaur dinosaur;
    private boolean running;
    private KeyHandler keyHandler;
    private SoundPlayer soundPlayer;
   

    public GamePanel(){
        this.dinosaur = new Dinosaur();
        this.running = true;
        this.keyHandler = new KeyHandler(dinosaur);
        this.soundPlayer = new SoundPlayer();
        addKeyListener(keyHandler);
        setFocusable(true);
        soundPlayer.setFile(1);
    }

    public void startGame(){
        this.running = true;
        new Thread(this).start();
        soundPlayer.play();
    }


    @Override
    public void run() {
        while (running) {
            updateGame();
            repaint();
            try {
                Thread.sleep(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        dinosaur.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        dinosaur.draw(g);
    }

}
