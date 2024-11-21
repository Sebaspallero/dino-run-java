package game.entities.obstacles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

import game.entities.Hitbox;
import game.utils.Animator;

public class Bird extends AbstractObstacle{
    private Animator animator;

    public Bird(){
        super(800, 140, 64, 64, new Hitbox(800, 150, 28, 28));

        try {
            Image birdSpriteSheet = ImageIO.read(getClass().getResource("/resources/sprites/blue-bird.png"));
            this.animator = new Animator(birdSpriteSheet, 32, 32, 9, 80, 0); // Configura el Animator
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(double deltaTime, int currentSpeed) {
        x -= deltaTime * currentSpeed;
        if (animator != null) {
            animator.update(); 
        }
        hitbox.update(x + 20, y + 20);
    }

    @Override
    public void draw(Graphics g) {
        if (animator != null) {
            animator.draw(g, x, y, width, height);
            /* hitbox.draw(g); */
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }
    }

}
