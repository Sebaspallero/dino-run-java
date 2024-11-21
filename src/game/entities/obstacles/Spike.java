package game.entities.obstacles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

import game.entities.Hitbox;

public class Spike extends AbstractObstacle{
    
    private Image image;

    public Spike(){
        super(800, 182, 48, 48, new Hitbox(800, 100, 22, 40));
        try {
            image = ImageIO.read(getClass().getResource("/resources/sprites/spikes-001.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
            /* hitbox.draw(g); */
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }
    }

}
