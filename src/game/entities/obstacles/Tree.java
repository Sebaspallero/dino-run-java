package game.entities.obstacles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

import game.entities.Hitbox;
import game.utils.Animator;

public class Tree extends AbstractObstacle {

    private WoodProyectile proyectile;
    private boolean hasAttacked;
    private State currentState;
    private Animator currentAnimator;
    private Animator attackAnimator;
    private Animator idleAnimator;
    private static final int PROJECTILE_LAUNCH_FRAME = 8;

    public enum State {
        IDLE,
        ATTACK,
        POST_ATTACK
    }

    public Tree() {
        super(800, 210, 64 * 2, 32 * 2, new Hitbox(800, 150, 30, 0));
        this.currentState = State.IDLE;
        this.hasAttacked = false;

        try {
            Image idleSprite = ImageIO.read(getClass().getResource("/resources/sprites/tree-idle.png"));
            this.idleAnimator = new Animator(idleSprite, 64, 32, 18, 50, 0); // Configura para 8 frames, 150ms cada uno

            Image attackSprite = ImageIO.read(getClass().getResource("/resources/sprites/tree-attack.png"));
            this.attackAnimator = new Animator(attackSprite, 64, 32, 11, 80, 1); // Configura para 11 frames, 80ms cada
                                                                                 
            this.currentAnimator = idleAnimator;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(double deltaTime, int currentSpeed) {
        x -= deltaTime * currentSpeed; 
        switch (currentState) {
            case IDLE:
                if (!hasAttacked && x < 700) { // Attack when tree is in that position
                    setCurrentState(State.ATTACK);
                }
                break;

            case ATTACK:
                currentAnimator.update(); // update animation of attack
                if (attackAnimator.getCurrentFrame() >= PROJECTILE_LAUNCH_FRAME && proyectile == null) {
                    proyectile = new WoodProyectile(this); 
                }
                if (proyectile != null) {
                    proyectile.update(deltaTime, currentSpeed); // update proyectile
                    if (proyectile.isOutOfScreen()) {
                        setCurrentState(State.POST_ATTACK);
                    }
                }

                // check if attack animation is over
                if (attackAnimator.isAnimationComplete()) {
                    // if yes, then change state
                    setCurrentState(State.POST_ATTACK);
                }
                break;

            case POST_ATTACK:
                // Transición de vuelta a IDLE si necesario
                if (x < -width) { // Cuando el árbol sale de la pantalla
                    setCurrentState(State.IDLE);
                }
                break;
        }

        //update proyectile despite the tree state
        if (proyectile != null) {
            proyectile.update(deltaTime, currentSpeed);
        }

        hitbox.update(x + 45, y + 20);

    }

    @Override
    public void draw(Graphics g) {
        if (currentAnimator != null) {
            currentAnimator.draw(g, x, y, width, height);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }

        if (proyectile != null) {
            proyectile.draw(g);
        }

        /* hitbox.draw(g); */
    }

    public void setCurrentState(State newState) {
        this.currentState = newState;

        switch (newState) {
            case IDLE:
                currentAnimator = idleAnimator;
                currentAnimator.reset();
                break;
            case ATTACK:
                currentAnimator = attackAnimator;
                currentAnimator.reset();
                break;
            case POST_ATTACK:
                currentAnimator = idleAnimator;
                currentAnimator.reset();
                break;
        }
    }

}