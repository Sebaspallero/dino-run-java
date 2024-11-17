package game.entities;
import java.awt.*;

public class Hitbox {
    private int x,y,height,width;

    public Hitbox(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void update(int newX, int newY){
        this.x = newX;
        this.y= newY;
    }

    public boolean intersects(Hitbox other) {
        return this.toRectangle().intersects(other.toRectangle());
    }

    public Rectangle toRectangle() {
        return new Rectangle(x, y, width, height);
    }

    //For debugging
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(x, y, width, height);
    }

}
