package game.entities.items;

import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import game.entities.Hitbox;
import game.utils.Animator;

public class Cherry extends AbstractItem {

    private State currentState;

    // Sprites estáticos cargados una sola vez
    private static Image idleSprite;
    private static Image collectedSprite;

    static {
        try {
            idleSprite = ImageIO.read(Cherry.class.getResource("/resources/sprites/cherries.png"));
            collectedSprite = ImageIO.read(Cherry.class.getResource("/resources/sprites/collected.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading Cherry sprites");
        }
    }

    public Cherry(int x, int y) {
        super(x, y, 64, 64, new Hitbox(x, y, 32, 32));
        this.currentState = State.IDLE;
        animator = new Animator(idleSprite, 64, 64, 17, 50, 0);
    }

    @Override
    public void update(double deltaTime, int currentSpeed) {
        x -= deltaTime * currentSpeed;
        if (animator != null) {
            animator.update();
        }
        hitbox.update(x + 20, y + 20);
    }

    public void setState(State newState) {
        if (newState != currentState) {
            this.currentState = newState;
            switch (newState) {
                case IDLE:
                    animator = new Animator(idleSprite, width, height, 17, 50, 0);
                    break;
                case COLLECTED:
                    animator = new Animator(collectedSprite, width, height, 17, 50, 0);
                    break;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        if (animator != null) {
            animator.draw(g, x, y, width, height);
        }
    }
}