package game.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import game.utils.FontLoader;
import game.utils.TextGenerator;

public class GameOverScreen {

    public static void gameOverScreen(Graphics g, int width, int height, int score){
        Font customFont = FontLoader.loadFont("/resources/font/PixelOperator8.ttf", 16f);
        Font customBoldFont = FontLoader.loadFont("/resources/font/PixelOperator8-Bold.ttf", 24f);
        
        FontMetrics metrics = g.getFontMetrics(customBoldFont);

        String gameOver = "GAME OVER";
        int gameOverX = (width - metrics.stringWidth(gameOver)) / 2;
        int gameOverY = height / 2 - 20;
        TextGenerator gameOverText = new TextGenerator(gameOver, gameOverX, gameOverY, customBoldFont, Color.RED);
        gameOverText.draw(g);

        metrics = g.getFontMetrics(customFont);

        String scoreText = "Your score is: " + score;
        int scoreX = (width - metrics.stringWidth(scoreText)) / 2;
        int scoreY = gameOverY + 30;
        TextGenerator scText = new TextGenerator(scoreText, scoreX, scoreY, customFont, Color.RED);
        scText.draw(g);
        

        String restart = "Press enter to play again!";
        int restartX = (width - metrics.stringWidth(restart)) / 2;
        int restartY = scoreY + 30;
        TextGenerator restartText = new TextGenerator(restart, restartX, restartY, customFont, Color.RED);
        restartText.draw(g);
    }
            
}
