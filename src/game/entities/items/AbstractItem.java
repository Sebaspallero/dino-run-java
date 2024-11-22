package game.entities.items;

import java.awt.*;
import game.entities.Hitbox;
import game.utils.Animator;

public abstract class AbstractItem {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Hitbox hitbox;
    protected Animator animator;
    protected State currState;

    public enum State {
        IDLE,
        COLLECTED
    }

    public AbstractItem(int x, int y, int width, int height, Hitbox hitbox) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = hitbox;
        this.currState = State.IDLE;
    }

    public AbstractItem(int x, int y, int width, int height, Hitbox hitbox, Animator animator) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = hitbox;
        this.animator = animator;
    }

    // Actualización del obstáculo
    public void update(double deltaTime, int currentSpeed) {
        x -= deltaTime * currentSpeed;
        hitbox.update(x, y + 25);
    }

    // Verifica si el obstáculo está fuera de la pantalla
    public boolean isOutOfScreen() {
        return x + width < -15;
    }

    // Dibujar el obstáculo (implementación específica en subclases)
    public abstract void draw(Graphics g);

    // Getters
    public Hitbox getHitbox() {
        return hitbox;
    }

    public State getCurrentState() {
        return currState;
    }
    
    public void setCurrentState(State newState){
        this.currState = newState;
    }

}