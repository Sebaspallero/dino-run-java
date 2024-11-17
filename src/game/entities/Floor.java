package game.entities;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.main.DinoGame;

public class Floor {

    private int x, y, width, height;
    private double speed;
    private int screen_width;
    private Image image;


    public Floor() {
        this.screen_width = DinoGame.SCREEN_WIDTH;
        this.width = 750*2;
        this.height = 60*2;
        this.x = 0;
        this.y = DinoGame.SCREEN_HEIGHT - height;
        this.speed = 5;
       

        try {
            image = ImageIO.read(getClass().getResource("/resources/sprites/floor-001.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(double deltaTime, int newSpeed){
        this.speed = newSpeed * deltaTime;;
        x -= speed;
        if (x + width <= 0) {
            x = 0;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);

        if ( x + width < screen_width) {
            g.drawImage(image, x + width, y, width, height, null);
        }
    }
}
