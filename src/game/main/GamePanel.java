package game.main;

import java.awt.*;

import game.entities.Dinosaur;
import game.entities.Obstacle;
import game.sound.SoundPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    private Dinosaur dinosaur;
    private List<Obstacle> obstacleList;
    private boolean running;

    private KeyHandler keyHandler;
    private SoundPlayer soundPlayer;


    private int currentSpeed = 5; // Velocidad inicial de los obstáculos
    private long lastObstacleTime = System.currentTimeMillis();
    private long obstacleInterval = 2000; // Tiempo entre obstáculos (en ms)
    private long lastSpeedIncreaseTime = System.currentTimeMillis();
    private long speedIncreaseInterval = 10000; // Incrementar velocidad cada 10 segundos
    private Random random;
   

    public GamePanel(){
        this.running = true;

        this.dinosaur = new Dinosaur();
        this.obstacleList = new ArrayList<>();

        this.keyHandler = new KeyHandler(dinosaur);
        this.soundPlayer = new SoundPlayer();
        this.random = new Random();

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
        for (int i = 0; i < obstacleList.size(); i++) {
            Obstacle obstacle = obstacleList.get(i);
            obstacle.update();

            // Eliminar obstáculos fuera de pantalla
            if (obstacle.isOutOfScreen()) {
                obstacleList.remove(i);
                i--;
            }
        }

        // Generar nuevos obstáculos
        generateObstacles();

        // Incrementar velocidad con el tiempo
        increaseSpeed();
    }

    private void generateObstacles() {
        long currentTime = System.currentTimeMillis();

        // Generar un nuevo obstáculo si ha pasado suficiente tiempo
        if (currentTime - lastObstacleTime >= obstacleInterval) {
            int width = random.nextInt(30) + 20; // Ancho entre 20 y 50 px
            int height = random.nextInt(40) + 30; // Alto entre 30 y 70 px

            obstacleList.add(new Obstacle(width, height, currentSpeed));
            lastObstacleTime = currentTime;
        }
    }

    private void increaseSpeed() {
        long currentTime = System.currentTimeMillis();

        // Incrementar la velocidad cada `speedIncreaseInterval`
        if (currentTime - lastSpeedIncreaseTime >= speedIncreaseInterval) {
            currentSpeed++;
            lastSpeedIncreaseTime = currentTime;

            // Opcional: reduce el tiempo entre obstáculos para aumentar dificultad
            obstacleInterval = Math.max(500, obstacleInterval - 200);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.WHITE);

        dinosaur.draw(g);

        for (Obstacle obstacle : obstacleList) {
            obstacle.draw(g);
        }
    }

}
