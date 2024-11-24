package game.entities.items;

import game.entities.AbstractEntity;
import game.entities.Hitbox;
import game.utils.Animator;

public abstract class AbstractItem extends AbstractEntity {

    protected Animator animator;
    protected State currentState;

    public AbstractItem(int x, int y, int width, int height, Hitbox hitbox) {
        super(x, y, width, height);
        this.hitbox = hitbox;
        this.currentState = State.IDLE;
    }

    public AbstractItem(int x, int y, int width, int height, Hitbox hitbox, Animator animator) {
        super(x, y, width, height);
        this.hitbox = hitbox;
        this.animator = animator;
    }

}