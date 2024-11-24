package game.entities.obstacles;

import game.entities.AbstractEntity;
import game.entities.Hitbox;
import game.utils.Animator;

public abstract class AbstractObstacle extends AbstractEntity {

    protected Animator animator;

    public AbstractObstacle(int x, int y, int width, int height, Hitbox hitbox) {
        super(x, y, width, height);
        this.hitbox = hitbox;
    }

    public AbstractObstacle(int x, int y, int width, int height, Hitbox hitbox, Animator animator) {
        super(x, y, width, height);
        this.hitbox = hitbox;
        this.animator = animator;
    }

}
