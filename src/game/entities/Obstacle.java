package game.entities;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Obstacle {

    private int x, y, width, height, speed;
    private Image image;
    private Hitbox hitbox;

    private static final int GROUND_Y = 300 - 118;
    private static final int SPAWN_X = 800;

    public Obstacle(int width, int height, int speed) {
        this.x = SPAWN_X;
        this.y = GROUND_Y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.hitbox = new Hitbox(x, y, height - 20, width - 5);

        try {
            image = ImageIO.read(getClass().getResource("/resources/sprites/spikes-001.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        x -= speed;
        hitbox.update(x, y + 25);
    }

    public boolean isOutOfScreen(){
       return x + width < 0;
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
            hitbox.draw(g);
        }else{
            g.setColor(Color.BLUE);         
            g.fillRect(x, y, width, height); 
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Hitbox getHitbox(){
        return this.hitbox;
    }
    
}
