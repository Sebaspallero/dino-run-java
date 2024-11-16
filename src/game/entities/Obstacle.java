package game.entities;
import java.awt.*;

public class Obstacle {

    private int x, y, width, height, speed;

    private static final int GROUND_Y = 300 - 20;
    private static final int SPAWN_X = 800;

    public Obstacle(int width, int height, int speed) {
        this.x = SPAWN_X;
        this.y = GROUND_Y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public void update() {
        x -= speed;
    }

    public boolean isOutOfScreen(){
       return x + width < 0;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);         
        g.fillRect(x, y, width, height); 
    }
    
    
    
}
