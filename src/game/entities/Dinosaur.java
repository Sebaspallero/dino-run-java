package game.entities;
import java.awt.*;

import javax.imageio.ImageIO;

import game.main.Animator;

//The main character of the game, includes the position, speed, jump logic
public class Dinosaur {

    //Atributes
    private int x, y, width, height;
    private int velocityY; 
    private boolean jumping; 

    private Animator animator;
    private Image spriteSheet;

    private static final int GROUND_Y = 300 - 138; //The position of the ground
    private static final double GRAVITY = 1;
    private static final int JUMP_STRENGTH = -17;

    //Constructor
    public Dinosaur() {
        this.x = 50; //Position of the dinosaur on the screen horizontally (fixed)
        this.y = GROUND_Y; // Position of the dinosaur on the screen vertically
        this.width = 60; //Width of the dinosaur
        this.height = 68; //Height of the dinosaur
        this.velocityY = 0; //Vertical speed
        this.jumping = false; //Check if dinosaur is in the air

        try {
            spriteSheet = ImageIO.read(getClass().getResource("/resources/sprites/dinosaur-jump-sheet.png"));
            animator = new Animator(spriteSheet, width, height, 6, 100, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void update() {
        if (jumping) {
            velocityY += GRAVITY;
            y += velocityY;

            if (y >= GROUND_Y) {
                y = GROUND_Y;
                jumping = false;
                velocityY = 0;
            }
        }
        animator.update();
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
        if (spriteSheet != null) {
            animator.draw(g, x, y, width, height);
        }else{
            g.setColor(Color.BLACK);         // Color del dinosaurio
            g.fillRect(x, y, width, height); // Dibuja un rectángulo en la posición actual
        }
       
    }
}
