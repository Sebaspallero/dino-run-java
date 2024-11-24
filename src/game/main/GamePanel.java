package game.main;

import java.awt.*;

import game.UI.CharacterFrame;
import game.UI.GameOverScreen;
import game.UI.Heart;
import game.UI.IntroScreen;
import game.UI.TitleScreen;
import game.entities.AbstractEntity;
import game.entities.character.Character;
import game.entities.items.AbstractItem;
import game.entities.obstacles.AbstractObstacle;
import game.entities.terrain.Background;
import game.entities.terrain.Floor;
import game.handlers.KeyHandler;
import game.manager.CollisionManager;
import game.manager.EntityManager;
import game.manager.LivesManager;
import game.manager.ScoreManager;
import game.manager.SpeedManager;
import game.utils.FontLoader;
import game.utils.SoundPlayer;
import game.utils.TextGenerator;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    //Game states
    public enum GameState {
        INTRO,
        TITLE,
        PLAYING,
        GAME_OVER
    }

    //Atributes

    private Character character;
    private Floor floor;
    private Background background;
    private CharacterFrame characterFrame;

    private LivesManager livesManager;
    private ScoreManager scoreManager;
    private SpeedManager speedManager;
    private CollisionManager collissionManager;
    private EntityManager entityManager;

    private Font customBoldFont;
    private Font customFont;

    private boolean running;
    private boolean gameOver;

    private KeyHandler keyHandler;
    private SoundPlayer soundPlayer;

    private int initialSpeed = 250;
    private long speedIncreaseInterval = 15000;

    private double deltaTime;
    private long lastTime;

    private Thread gameThread;

    private GameState gameState;
    private long introStartTime;
    private boolean showTitleOnce = true;

    //Constructor

    public GamePanel() {
        this.running = false;

        this.gameState = GameState.INTRO;
        this.introStartTime = System.currentTimeMillis();

        this.character = new Character(this);
        this.floor = new Floor();
        this.background = new Background();
        this.characterFrame = new CharacterFrame();

        this.scoreManager = new ScoreManager();
        this.livesManager = new LivesManager();
        this.speedManager = new SpeedManager(initialSpeed, speedIncreaseInterval);
        this.collissionManager = new CollisionManager(new SoundPlayer(), livesManager, scoreManager);
        this.entityManager = new EntityManager(this, character);

        this.customBoldFont = FontLoader.loadFont("/resources/font/AvenuePixelStroke-Regular.ttf", 40f);
        this.customFont = FontLoader.loadFont("/resources/font/AvenuePixel-Regular.ttf", 40f);

        this.keyHandler = new KeyHandler(character, this);
        this.soundPlayer = new SoundPlayer();

        this.gameOver = false;

        addKeyListener(keyHandler);
        setFocusable(true);

        this.lastTime = System.nanoTime();

    }

    //Start the game for the first time

    public void startGame() {
        if (!running) {
            this.running = true;
            if (gameThread == null) {
                gameThread = new Thread(this);
                gameThread.start();
                soundPlayer.setFile(3);
                soundPlayer.loop();
            }
        }
    }

    //Restart the game after Game Over
    public void resetGame() {
        this.running = false;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.gameOver = false;
        this.entityManager.resetEntities();
        this.speedManager.resetSpeed(initialSpeed);
        this.scoreManager.resetScore();
        this.livesManager.resetHearts();
        this.collissionManager.setHitStartTime(0);
        this.character = new Character(this);
        this.floor = new Floor();
        this.background = new Background();

        this.keyHandler.setcharacter(character);
        this.lastTime = System.nanoTime();
        this.gameState = showTitleOnce ? GameState.PLAYING : GameState.TITLE;

        this.running = true;
        new Thread(this).start();

        repaint();
    }

    @Override
    public void run() {
        while (running) {
            long startTime = System.nanoTime(); // Start time to measure deltaTime
            updateGame();
            repaint();

            long elapsedTime = System.nanoTime() - startTime;
            long sleepTime = Math.max(0, (16_666_667L - elapsedTime) / 1000000); // Aim for 60 FPS (16ms per frame)
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Update the game changes in every loop

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
                character.update();
                background.update(deltaTime, 100);
                break;

            case PLAYING:
                if (gameOver)
                    return;

                if (collissionManager.ischaracterHit(character)) {
                    character.setCurrentState(Character.State.RUNNING);
                }
                speedManager.update();
                character.update();
                scoreManager.update();
                floor.update(deltaTime, speedManager.getCurrentSpeed());
                background.update(deltaTime, 100);
                entityManager.update(deltaTime, speedManager.getCurrentSpeed(), character);
                collissionManager.handleCollisions(character, entityManager.getEntityList());

                if (livesManager.checkHearts()) {
                    gameOver = true;
                    gameState = GameState.GAME_OVER;
                }
                break;

            case GAME_OVER:
                break;
        }
    }

    //Choose what to paint depending on the game state

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (gameState) {
            case INTRO:
                IntroScreen.drawIntroScreen(g, customBoldFont, getWidth(), getHeight());
                break;

            case TITLE:
                TitleScreen.drawTitleScreen(g, customFont, getWidth(), getHeight(), this, background, floor, character);
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

    //Draw the game on the screen

    private void drawGame(Graphics g) {
        background.draw(g, getWidth(), getHeight());
        floor.draw(g);
        character.draw(g);
        characterFrame.draw(g);

        for (AbstractEntity entity : entityManager.getEntityList()) {
            if (entity instanceof AbstractItem) {
                entity.draw(g);
            } else if (entity instanceof AbstractObstacle) {
                entity.draw(g);
            }
        }

        for (Heart heart : livesManager.getHearts()) {
            heart.draw(g);
        }

        String score = "" + scoreManager.getScore();
        FontMetrics metrics = g.getFontMetrics(customBoldFont);
        int textWidth = metrics.stringWidth(score);
        int posX = (getWidth() - textWidth) / 2;
        new TextGenerator(score, posX, 40, customFont, Color.WHITE).draw(g);
    }

    //Helper methods and Getters - Setters

    private void getDeltaTime() {
        long now = System.nanoTime();
        deltaTime = (now - lastTime) / 1000000000.0;
        lastTime = now;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
