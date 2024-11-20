package game.main;

import java.awt.*;

import game.UI.GameOverScreen;
import game.UI.Heart;
import game.entities.Background;
import game.entities.Floor;
import game.entities.character.Dinosaur;
import game.entities.obstacles.Obstacle;
import game.handlers.KeyHandler;
import game.manager.CollisionManager;
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
    private CollisionManager collissionManager;
    private ObstacleManager obstacleManager;


    @SuppressWarnings("unused")
    private Font customBoldFont;
    private Font customFont;

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
        this.collissionManager = new CollisionManager(new SoundPlayer(), livesManager);
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
        this.collissionManager.setHitStartTime(0);
        this.lastTime = System.nanoTime();

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

        if (dinosaur.getCurrentState() == Dinosaur.State.HIT) {
            dinosaur.update(); // Actualiza la animación de HIT
            if (collissionManager.isDinosaurHit(dinosaur)) {
                gameOver = true;
            }
            return; // Salimos del ciclo de actualización, pero dejamos que la animación se ejecute
        }

        speedManager.update();
        dinosaur.update();
        scoreManager.update();
        floor.update(deltaTime, speedManager.getCurrentSpeed());
        obstacleManager.update(deltaTime, speedManager.getCurrentSpeed());

        collissionManager.handleCollisions(dinosaur, obstacleManager.getObstacleList());
    }

    private void getDeltaTime(){
        long now = System.nanoTime();
        deltaTime = (now - lastTime) / 1000000000.0;
        lastTime = now;
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
