package game.entities.obstacles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

import game.entities.Hitbox;
import game.utils.Animator;

public class Pig extends AbstractObstacle {
    private Animator animator;

    public Pig() {
        super(800, 210, 36 * 2, 30 * 2, new Hitbox(800, 150, 35, 35));

        try {
            Image birdSpriteSheet = ImageIO.read(getClass().getResource("/resources/sprites/pig-run.png"));
            this.animator = new Animator(birdSpriteSheet, 36, 30, 12, 50, 0); // Configura el Animator
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
        hitbox.update(x + 20, y + 10);
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