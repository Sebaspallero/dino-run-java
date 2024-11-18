package game.main;

import java.awt.*;

import game.entities.Background;
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
    private Background background;

    private Font customBoldFont;
    private Font customFont;

    private int score;
    private int scoreTimer;
    private final int SCORE_DELAY = 7;

    private long hitStartTime;
    private final long HIT_DURATION = 1000;

    private boolean running;
    private boolean gameOver;

    private KeyHandler keyHandler;
    private SoundPlayer soundPlayer;

    private int currentSpeed = 300; // Velocidad inicial de los obstáculos
    private long lastObstacleTime = System.currentTimeMillis();
    private long obstacleInterval = 2000; // Tiempo entre obstáculos (en ms)
    private long lastSpeedIncreaseTime = System.currentTimeMillis();
    private long speedIncreaseInterval = 10000; // Incrementar velocidad cada 10 segundos

    private double deltaTime;  // Variable para almacenar el deltaTime
    private long lastTime;

    public GamePanel() {
        this.running = false;

        this.dinosaur = new Dinosaur();
        this.obstacleList = new ArrayList<>();
        this.floor = new Floor();
        this.background = new Background();
        this.score = 0;
        this.scoreTimer = 0;
        
        customFont = FontLoader.loadFont("/resources/font/PixelOperator8.ttf", 16f);
        customBoldFont = FontLoader.loadFont("/resources/font/PixelOperator8-Bold.ttf", 24f);

        this.keyHandler = new KeyHandler(dinosaur,this);
        this.soundPlayer = new SoundPlayer();

        this.gameOver = false;

        addKeyListener(keyHandler);
        setFocusable(true);

        this.lastTime = System.nanoTime();
        
    }

    public void startGame() {
        if (!running) {
            this.running = true;
            new Thread(this).start();
    
            soundPlayer.setFile(1);
            soundPlayer.play();
        }
    }

    public void resetGame(){
        this.running = false;

        try {
            Thread.sleep(100);  // Dar tiempo para detener el hilo correctamente
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.gameOver = false;
        this.currentSpeed = 300;  // Reinicia la velocidad
        this.lastObstacleTime = System.currentTimeMillis();
        this.lastSpeedIncreaseTime = System.currentTimeMillis();
        this.obstacleList.clear();  // Limpia los obstáculos existentes
        this.obstacleInterval = 2000;

        this.dinosaur = new Dinosaur();  // Reinicia el dinosaurio
        this.floor = new Floor();  // Reinicia el suelo
        this.background = new Background();  // Reinicia el fondo
        this.score = 0;
        this.scoreTimer = 0;

        this.keyHandler.setDinosaur(this.dinosaur);

        this.lastTime = System.nanoTime();  // Reiniciar el cálculo de deltaTime
        this.hitStartTime = 0;  // Reinicia el tiempo de colisión

        
        // Luego de reiniciar, se puede volver a ejecutar el juego:
        this.running = true;
        new Thread(this).start();  // Iniciar un nuevo hilo de ejecución
        repaint();
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
        if (!running) {
            return;
        }
    
        if (gameOver) {
            // Solo actualizamos la pantalla de Game Over, pero no hacemos más lógica de juego
            return;
        }
    
        // Calcular deltaTime
        long now = System.nanoTime();
        deltaTime = (now - lastTime) / 1000000000.0; // Convertir a segundos
        lastTime = now;
    
        // Si el dinosaurio está en estado HIT
        if (dinosaur.getCurrentState() == Dinosaur.State.HIT) {
            dinosaur.update(); // Asegurarse de que la animación de HIT se actualice
            long elapsedTime = System.currentTimeMillis() - hitStartTime;
    
            if (elapsedTime >= HIT_DURATION) { // Pasar a gameOver después de la animación
                gameOver = true;
                // Mantenemos el juego en estado "gameOver", pero no detenemos el juego
                // No cambiamos running = false aquí
            }
    
            return; // No actualizamos más elementos
        }
    
        // Actualizar elementos del juego si no estamos en HIT
        increaseSpeed();
        dinosaur.update();
        floor.update(deltaTime, currentSpeed);
    
        for (int i = 0; i < obstacleList.size(); i++) {
            Obstacle obstacle = obstacleList.get(i);
            obstacle.update(deltaTime, currentSpeed);
    
            if (dinosaur.geHitbox().intersects(obstacle.getHitbox())) {
                if (dinosaur.getCurrentState() != Dinosaur.State.HIT) {
                    dinosaur.onCollision();
                    hitStartTime = System.currentTimeMillis();
                    soundPlayer.setFile(2);
                    soundPlayer.play();
                }
            }
    
            if (obstacle.isOutOfScreen()) {
                obstacleList.remove(i);
                i--;
            }
        }
    
        // Generar el puntaje
        scoreTimer++;
        if (scoreTimer >= SCORE_DELAY) {
            score++;
            scoreTimer = 0;
        }
    
        // Generar nuevos obstáculos
        generateObstacles();
    }

    private void generateObstacles() {
        long currentTime = System.currentTimeMillis();

        // Generar un nuevo obstáculo si ha pasado suficiente tiempo
        if (currentTime - lastObstacleTime >= obstacleInterval) {
            int width = 48;
            int height = 48;

            obstacleList.add(new Obstacle(width, height));
            lastObstacleTime = currentTime;
        }
    }

    private void increaseSpeed() {
        long currentTime = System.currentTimeMillis();

        // Incrementar la velocidad cada `speedIncreaseInterval`
        if (currentTime - lastSpeedIncreaseTime >= speedIncreaseInterval) {
            currentSpeed += 50;
            lastSpeedIncreaseTime = currentTime;

            // Opcional: reduce el tiempo entre obstáculos para aumentar dificultad
            obstacleInterval = Math.max(500, obstacleInterval - 200);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        background.draw(g, getWidth(), getHeight());
        floor.draw(g);
        dinosaur.draw(g);

        for (Obstacle obstacle : obstacleList) {
            obstacle.draw(g);
        }

        g.setColor(Color.WHITE);
        g.setFont(customFont);
        g.drawString("Score: " + score, 20, 20);

        if (gameOver) {
            g.setColor(Color.RED); 
            g.setFont(customBoldFont);

            FontMetrics metrics = g.getFontMetrics(customBoldFont);
            String gameOver = "GAME OVER";
            int gameOverX = (getWidth() - metrics.stringWidth(gameOver)) / 2;
            int gameOverY = getHeight() / 2 - 20;
            g.drawString(gameOver, gameOverX, gameOverY);

           
            g.setFont(customFont);
            metrics = g.getFontMetrics(customFont);

            String scoreText = "Your score is: " + score;
            int scoreX = (getWidth() - metrics.stringWidth(scoreText)) / 2;
            int scoreY = gameOverY + 30;
            g.drawString(scoreText, scoreX, scoreY);
            

            String restartText = "Press enter to play again!";
            int restartX = (getWidth() - metrics.stringWidth(restartText)) / 2;
            int restartY = scoreY + 30;
            g.drawString(restartText, restartX, restartY);
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
