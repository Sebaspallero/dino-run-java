package game.main;

import java.awt.*;

import game.UI.CharacterFrame;
import game.UI.GameOverScreen;
import game.UI.Heart;
import game.entities.character.Dinosaur;
import game.entities.items.AbstractItem;
import game.entities.obstacles.AbstractObstacle;
import game.entities.terrain.Background;
import game.entities.terrain.Floor;
import game.handlers.KeyHandler;
import game.manager.CollisionManager;
import game.manager.ItemManager;
import game.manager.LivesManager;
import game.manager.ObstacleManager;
import game.manager.ScoreManager;
import game.manager.SpeedManager;
import game.utils.FontLoader;
import game.utils.SoundPlayer;
import game.utils.TextGenerator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    public enum GameState {
        INTRO,
        TITLE,
        PLAYING,
        GAME_OVER
    }

    private Dinosaur dinosaur;
    private Floor floor;
    private Background background;
    private CharacterFrame characterFrame;

    private LivesManager livesManager;
    private ScoreManager scoreManager;
    private SpeedManager speedManager;
    private CollisionManager collissionManager;
    private ObstacleManager obstacleManager;
    private ItemManager itemManager;

    private Font customBoldFont;

    private boolean running;
    private boolean gameOver;

    private KeyHandler keyHandler;
    private SoundPlayer soundPlayer;

    private int initialSpeed = 250;
    private long speedIncreaseInterval = 15000; //Every 15 seconds increase the speed
    
    private double deltaTime; 
    private long lastTime;

    private GameState gameState;
    private long introStartTime;
    private boolean showTitleOnce = true;
    private ImageIcon pressSpaceGif;

    public GamePanel() {
        this.running = false;

        this.gameState = GameState.INTRO;
        this.introStartTime = System.currentTimeMillis();

        this.dinosaur = new Dinosaur();
        this.floor = new Floor();
        this.background = new Background();
        this.characterFrame = new CharacterFrame();

        this.scoreManager = new ScoreManager();
        this.livesManager = new LivesManager();
        this.speedManager = new SpeedManager(initialSpeed, speedIncreaseInterval);
        this.collissionManager = new CollisionManager(new SoundPlayer(), livesManager, scoreManager);
        this.obstacleManager = new ObstacleManager();
        this.itemManager = new ItemManager();
        
        this.customBoldFont = FontLoader.loadFont("/resources/font/AvenuePixelStroke-Regular.ttf", 40f);
        this.pressSpaceGif = new ImageIcon(GamePanel.class.getResource("/resources/sprites/title-screen.gif"));

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
            soundPlayer.setFile(3);
            soundPlayer.loop();
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
        this.itemManager.resetItems();
        this.speedManager.resetSpeed(initialSpeed);
        this.scoreManager.resetScore();
        this.livesManager.resetHearts();
        this.collissionManager.setHitStartTime(0);
        this.dinosaur = new Dinosaur();  
        this.floor = new Floor();  
        this.background = new Background(); 
       
        
        this.keyHandler.setDinosaur(this.dinosaur);
        this.lastTime = System.nanoTime();
        this.gameState = showTitleOnce ? GameState.PLAYING : GameState.TITLE;

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
        if (!running) return;
        getDeltaTime();
        switch (gameState) {
            case INTRO:
                if (System.currentTimeMillis() - introStartTime >= 5000) {
                    gameState = GameState.TITLE;
                }
                break;
    
            case TITLE:
                dinosaur.update();
                background.update(deltaTime, 100);
                break; 
    
            case PLAYING:
                if (gameOver) return;

                if (collissionManager.isDinosaurHit(dinosaur)) {
                    dinosaur.setCurrentState(Dinosaur.State.RUNNING); 
                }
                speedManager.update();
                dinosaur.update();
                scoreManager.update();
                floor.update(deltaTime, speedManager.getCurrentSpeed());
                background.update(deltaTime, 100);
                obstacleManager.update(deltaTime, speedManager.getCurrentSpeed());
                collissionManager.handleObstacleCollisions(dinosaur, obstacleManager.getObstacleList());
                collissionManager.handleCherryCollisions(dinosaur, itemManager.getCherryList());
    
                if (livesManager.checkHearts()) {
                    gameOver = true;
                    gameState = GameState.GAME_OVER;
                }
                break;
    
            case GAME_OVER:
                break; // Ya se gestiona el Game Over en `paintComponent`.
        }
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

    switch (gameState) {
        case INTRO:
            drawIntroScreen(g);
            break;

        case TITLE:
            drawTitleScreen(g);
            break;

        case PLAYING:
            drawGame(g);
            break;

        case GAME_OVER:
            drawGame(g);
            GameOverScreen.gameOverScreen(g, getWidth(), getHeight(), scoreManager.getScore(), this);
            break;
        }
    }

    // Métodos de dibujo personalizados
private void drawIntroScreen(Graphics g) {
    g.setColor(new Color(33, 31, 48)); // Fondo negro
    g.fillRect(0, 0, getWidth(), getHeight());

    g.setColor(new Color(255, 255, 255));
    g.setFont(customBoldFont.deriveFont(50f));
    g.drawString("Created by", getWidth() / 2 - 150, getHeight() / 2);
    g.drawString("Sebastián Pallero Oría", getWidth() / 2 - 100, getHeight() / 2 + 60);
}

private void drawTitleScreen(Graphics g) {
    background.draw(g, getWidth(), getHeight());
    floor.draw(g);
    dinosaur.draw(g);

    if (pressSpaceGif != null) {
        pressSpaceGif.paintIcon(this, g, getWidth() / 2 - 200, -30);
    }
    g.setFont(customBoldFont.deriveFont(30f));
    g.drawString("Press SPACE to start", getWidth() / 2 - 150, getHeight() / 2);
}

private void drawGame(Graphics g) {
    background.draw(g, getWidth(), getHeight());
    floor.draw(g);
    dinosaur.draw(g);
    characterFrame.draw(g);
    
    

    for (AbstractObstacle obstacle : obstacleManager.getObstacleList()) {
        obstacle.draw(g);
    }

    for (Heart heart : livesManager.getHearts()) {
        heart.draw(g);
    }

    
    String score = "" + scoreManager.getScore();
    FontMetrics metrics = g.getFontMetrics(customBoldFont);
    int textWidth = metrics.stringWidth(score);
    int posX = (getWidth() - textWidth) / 2;
    new TextGenerator(score, posX, 40, customBoldFont, Color.WHITE).draw(g);
}

public GameState getGameState() {
    return gameState;
}

public void setGameState(GameState gameState) {
    this.gameState = gameState;
}
}
