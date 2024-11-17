package game.main;

import java.awt.*;

import game.entities.Dinosaur;
import game.entities.Floor;
import game.entities.Obstacle;
import game.sound.SoundPlayer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    private Dinosaur dinosaur;
    private List<Obstacle> obstacleList;
    private Floor floor;
    

    private boolean running;
    private boolean gameOver;

    private KeyHandler keyHandler;
    private SoundPlayer soundPlayer;

    private int currentSpeed = 5; // Velocidad inicial de los obstáculos
    private long lastObstacleTime = System.currentTimeMillis();
    private long obstacleInterval = 2000; // Tiempo entre obstáculos (en ms)
    private long lastSpeedIncreaseTime = System.currentTimeMillis();
    private long speedIncreaseInterval = 10000; // Incrementar velocidad cada 10 segundos


    public static final int GROUND_HEIGHT = 50;

    public GamePanel() {
        this.running = true;

        this.dinosaur = new Dinosaur();
        this.obstacleList = new ArrayList<>();
        this.floor = new Floor(currentSpeed);


        this.keyHandler = new KeyHandler(dinosaur);
        this.soundPlayer = new SoundPlayer();
        this.gameOver = false;

        addKeyListener(keyHandler);
        setFocusable(true);
        soundPlayer.setFile(1);

    }

    public void startGame() {
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
        if (gameOver) {
            return; // Si el juego está terminado, no actualizamos más
        }

        dinosaur.update();
        floor.update();

        for (int i = 0; i < obstacleList.size(); i++) {
            Obstacle obstacle = obstacleList.get(i);
            obstacle.update();

            if (dinosaur.checkCollision(obstacle)) {
                gameOver = true; // Terminar el juego en caso de colisión
            }

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
            int width = 24;//random.nextInt(30) + 20; // Ancho entre 20 y 50 px
            int height = 24;//random.nextInt(40) + 30; // Alto entre 30 y 70 px

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

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(g.getFont().deriveFont(50.0f));
            g.drawString("GAME OVER!", getWidth() / 2 - 150, getHeight() / 2);
        }

        floor.draw(g);
        dinosaur.draw(g);

        for (Obstacle obstacle : obstacleList) {
            obstacle.draw(g);
        }
    }

}
