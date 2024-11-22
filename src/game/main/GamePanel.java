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

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

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
    /* private Font customFont; */

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
        this.characterFrame = new CharacterFrame();

        this.scoreManager = new ScoreManager();
        this.livesManager = new LivesManager();
        this.speedManager = new SpeedManager(initialSpeed, speedIncreaseInterval);
        this.collissionManager = new CollisionManager(new SoundPlayer(), livesManager);
        this.obstacleManager = new ObstacleManager();
        this.itemManager = new ItemManager();
        
        /* this.customFont = FontLoader.loadFont("/resources/font/AvenuePixelStroke-Regular.ttf", 16f); */
        this.customBoldFont = FontLoader.loadFont("/resources/font/AvenuePixelStroke-Regular.ttf", 40f);

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
    
            /* soundPlayer.setFile(1);
            soundPlayer.play(); */
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

        if (collissionManager.isDinosaurHit(dinosaur)) {
            dinosaur.setCurrentState(Dinosaur.State.RUNNING); 
        }

        speedManager.update();
        dinosaur.update();
        scoreManager.update();
        floor.update(deltaTime, speedManager.getCurrentSpeed());
        background.update(deltaTime, 100);
        obstacleManager.update(deltaTime, speedManager.getCurrentSpeed());
        itemManager.update(deltaTime, speedManager.getCurrentSpeed());
        collissionManager.handleObstacleCollisions(dinosaur, obstacleManager.getObstacleList());
        collissionManager.handleCherryCollisions(dinosaur, itemManager.getCherryList());

        if (livesManager.checkHearts()) {
            gameOver = true;
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

        background.draw(g, getWidth(), getHeight());

        /* try {
            Image image = ImageIO.read(getClass().getResource("/resources/sprites/title-screen.png"));
            g.drawImage(image, 300, 115, null);
        } catch (IOException e) {
            e.printStackTrace();
        } */
       

        floor.draw(g);
        dinosaur.draw(g);
        characterFrame.draw(g);

        for (Heart heart : livesManager.getHearts()) {
            heart.draw(g);
        }

        for (AbstractObstacle obstacle : obstacleManager.getObstacleList()) {
            obstacle.draw(g);
        }

        for (AbstractItem item : itemManager.getCherryList()) {
            item.draw(g);
        }

        String score = "" + scoreManager.getScore();
        FontMetrics metrics = g.getFontMetrics(customBoldFont);
        int textWidth = metrics.stringWidth(score);

        int posX = (getWidth() - textWidth) / 2;

        TextGenerator scoreText = new TextGenerator(score, posX, 40, customBoldFont, Color.WHITE);
        scoreText.draw(g);

        if (gameOver) {
            GameOverScreen.gameOverScreen(g, getWidth(), getHeight(), scoreManager.getScore());
        }
    }
}
