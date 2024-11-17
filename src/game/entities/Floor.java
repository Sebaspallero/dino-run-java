package game.entities;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.main.DinoGame;

public class Floor {

    private int x, y, width, height;
    private int speed;
    private int screen_width;
    private Image image;


    public Floor(int speed) {
        this.screen_width = DinoGame.SCREEN_WIDTH;
        this.width = 750*2;
        this.height = 60*2;
        this.x = 0;
        this.y = DinoGame.SCREEN_HEIGHT - height;
        this.speed = speed;
       

        try {
            image = ImageIO.read(getClass().getResource("/resources/sprites/floor-001.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
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
