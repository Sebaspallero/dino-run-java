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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public static int getGroundY() {
        return GROUND_Y;
    }

    public static int getSpawnX() {
        return SPAWN_X;
    }
    
    
    
}
