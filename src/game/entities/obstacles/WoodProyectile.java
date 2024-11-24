package game.entities.obstacles;

import java.awt.Graphics;

import javax.imageio.ImageIO;
import java.awt.Image;

import game.entities.Hitbox;

public class WoodProyectile extends AbstractObstacle {

    private int speed;
    private Image image;

    public WoodProyectile(Tree tree) {
        super(tree.getX() + 10, tree.getY() + 25, 16, 16, new Hitbox(tree.getX() + 10, tree.getY() + 10, 16, 16));
        this.speed = 2;

        try {
            image = ImageIO.read(getClass().getResource("/resources/sprites/Bullet.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(double deltaTime, int currentSpeed) {
        x -= deltaTime * currentSpeed * speed;
        hitbox.update(x, y);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
        hitbox.draw(g);
    }

}
