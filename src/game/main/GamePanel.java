package game.main;

import java.awt.*;

import game.UI.GameOverScreen;
import game.UI.Heart;
import game.entities.Background;
import game.entities.Dinosaur;
import game.entities.Floor;
import game.entities.Obstacle;
import game.handlers.KeyHandler;
import game.manager.CollissionManager;
import game.manager.LivesManager;
import game.manager.ObstacleManager;
import game.manager.ScoreManager;
import game.manager.SpeedManager;
import game.utils.FontLoader;
import game.utils.SoundPlayer;
import game.utils.TextGenerator;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    private Dinosaur dinosaur;
    private Floor floor;
    private Background background;

    private LivesManager livesManager;
    private ScoreManager scoreManager;
    private SpeedManager speedManager;
    private CollissionManager collissionManager;
    private ObstacleManager obstacleManager;


    @SuppressWarnings("unused")
    private Font customBoldFont;
    private Font customFont;

    private long hitStartTime;
    private final long HIT_DURATION = 1000;

    private boolean running;
    private boolean gameOver;

    private KeyHandler keyHandler;
    private SoundPlayer soundPlayer;

    private int initialSpeed = 250;
    private long speedIncreaseInterval = 15000; //Every 10 seconds increase the speed
    
    private double deltaTime; 
    private long lastTime;

    public GamePanel() {
        this.running = false;

        this.dinosaur = new Dinosaur();
        this.floor = new Floor();
        this.background = new Background();

        this.scoreManager = new ScoreManager();
        this.livesManager = new LivesManager();
        this.speedManager = new SpeedManager(initialSpeed, speedIncreaseInterval);
        this.collissionManager = new CollissionManager();
        this.obstacleManager = new ObstacleManager();
        
        this.customFont = FontLoader.loadFont("/resources/font/PixelOperator8.ttf", 16f);
        this.customBoldFont = FontLoader.loadFont("/resources/font/PixelOperator8-Bold.ttf", 24f);

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
            Thread.sleep(100);  
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.gameOver = false;
        
        this.obstacleManager.resetObstacles();
        this.speedManager.resetSpeed(initialSpeed);
        this.scoreManager.resetScore();
        this.livesManager.resetHearts();
        this.dinosaur = new Dinosaur();  
        this.floor = new Floor();  
        this.background = new Background(); 
        
        this.keyHandler.setDinosaur(this.dinosaur);

        this.lastTime = System.nanoTime();  
        this.hitStartTime = 0;

        this.running = true;
        new Thread(this).start();
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
        if (!running || gameOver) {
            return;
        }
    
        //Calculate Delta Time
        getDeltaTime();
        
        //Check state of dinosaur
        if (isDinosaurHit()) {
            return;
        }

        speedManager.update();
        dinosaur.update();
        scoreManager.update();
        floor.update(deltaTime, speedManager.getCurrentSpeed());
        obstacleManager.update(deltaTime, speedManager.getCurrentSpeed());

        handleCollisions();
    }

    private void getDeltaTime(){
        long now = System.nanoTime();
        deltaTime = (now - lastTime) / 1000000000.0;
        lastTime = now;
    }

    private boolean isDinosaurHit(){
        if (dinosaur.getCurrentState() == Dinosaur.State.HIT) {
            dinosaur.update(); // Actualiza animación de HIT
            long elapsedTime = System.currentTimeMillis() - hitStartTime;
    
            if (elapsedTime >= HIT_DURATION) {
                gameOver = true;
            }
            return true; // Indica que el dinosaurio está en estado HIT
        }
        return false; // Continúa con la actualización normal
    }

    private void handleCollisions() {
        boolean collisionDetected = false;
    
        for (int i = 0; i < obstacleManager.getObstacleList().size(); i++) {
            Obstacle obstacle = obstacleManager.getObstacleList().get(i);

            if (collissionManager.checkCollision(dinosaur, obstacleManager.getObstacleList())) {
                collisionDetected = true;
    
                if (!dinosaur.hasCollided()) {
                    handleDinosaurCollision();
                }
                break; // Salimos del bucle si detectamos una colisión
            }
    
            if (obstacle.isOutOfScreen()) {
                obstacleManager.getObstacleList().remove(i);
                i--;
            }
        }
    
        if (!collisionDetected && dinosaur.hasCollided()) {
            dinosaur.setCollided(false); // Restablece el estado del dinosaurio
        }
    }

    private void handleDinosaurCollision() {
        dinosaur.onCollision();
        soundPlayer.setFile(2);
        soundPlayer.play();
        hitStartTime = System.currentTimeMillis();
        livesManager.updateHeart(dinosaur);
    }
    
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        background.draw(g, getWidth(), getHeight());
        floor.draw(g);
        dinosaur.draw(g);

        for (Heart heart : livesManager.getHearts()) {
            heart.draw(g);
        }

        for (Obstacle obstacle : obstacleManager.getObstacleList()) {
            obstacle.draw(g);
        }

        TextGenerator scoreText = new TextGenerator("Score: " + scoreManager.getScore(), 20, 20, customFont, Color.WHITE);
        scoreText.draw(g);

        if (gameOver) {
            GameOverScreen.gameOverScreen(g, getWidth(), getHeight(), scoreManager.getScore());
        }
    }
}
