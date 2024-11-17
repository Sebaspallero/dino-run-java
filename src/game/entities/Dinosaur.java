package game.entities;
import java.awt.*;

//The main character of the game, includes the position, speed, jump logic
public class Dinosaur {

    //Atributes
    private int x, y, width, height;
    private int velocityY; 
    private boolean jumping; 

    private static final int GROUND_Y = 300 - 29; //The position of the ground
    private static final double GRAVITY = 1;
    private static final int JUMP_STRENGTH = -17;

    //Constructor
    public Dinosaur() {
        this.x = 50; //Position of the dinosaur on the screen horizontally (fixed)
        this.y = GROUND_Y; // Position of the dinosaur on the screen vertically
        this.width = 40; //Width of the dinosaur
        this.height = 40; //Height of the dinosaur
        this.velocityY = 0; //Vertical speed
        this.jumping = false; //Check if dinosaur is in the air
    }

    public void update() {
        if (jumping) {
            velocityY += GRAVITY;
            y += velocityY;

            if (y == GROUND_Y) {
                y = GROUND_Y;
                jumping = false;
                velocityY = 0;
            }
        }
    }

    public void jump() {
        if (!jumping) {
            jumping = true;           
            velocityY = JUMP_STRENGTH;
        }
    }

    public boolean checkCollision(Obstacle obstacle) {
        return x + width > obstacle.getX() && x < obstacle.getX() + obstacle.getWidth() &&
               y + height > obstacle.getY() && y < obstacle.getY() + obstacle.getHeight();
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);         // Color del dinosaurio
        g.fillRect(x, y, width, height); // Dibuja un rectángulo en la posición actual
    }
}
