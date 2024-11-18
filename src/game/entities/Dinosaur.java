package game.entities;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import game.main.Animator;

public class Dinosaur {

    public enum State {
        RUNNING, CROUCHING, HIT
    }

    //Atributes
    private final int x;
    private int y;
    private final int width;
    private final int height;
    private int velocityY; 
    private boolean jumping; 

    private Hitbox hitbox;
    private State currentState;
    private Map<State, Animator> animations;

    private static final int GROUND_Y = 152; //The position of the ground
    private static final double GRAVITY = 1;
    private static final int JUMP_STRENGTH = -17;

    //Constructor
    public Dinosaur() {
        this.x = 50; //Position of the dinosaur on the screen horizontally (fixed)
        this.y = GROUND_Y; // Position of the dinosaur on the screen vertically
        this.width = 96; //Width of the dinosaur
        this.height = 96; //Height of the dinosaur
        this.velocityY = 0; //Vertical speed
        this.jumping = false; //Check if dinosaur is in the air
        this.currentState = State.RUNNING;

        this.animations = new HashMap<>();
        this.hitbox = new Hitbox(x, y, (int) (height * 0.6), (int) (width * 0.5));

        initializeAnimations();
    }

    public void initializeAnimations(){
        try {
            // Cargar y mapear animaciones
            Image runningSheet = ImageIO.read(getClass().getResource("/resources/sprites/dinosaur-run-sheet.png"));
            animations.put(State.RUNNING, new Animator(runningSheet, 32 * 3, 32 * 3, 6, 100, 0));

            Image crouchingSheet = ImageIO.read(getClass().getResource("/resources/sprites/dinosaur-crouch-sheet.png"));
            animations.put(State.CROUCHING, new Animator(crouchingSheet, 32 * 3, 32 * 3, 6, 100, 0));

            Image hitSheet = ImageIO.read(getClass().getResource("/resources/sprites/dinosaur-hurt-sheet.png"));
            animations.put(State.HIT, new Animator(hitSheet, 32 * 3, 32 * 3, 4, 150, 0));

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
                currentState = State.RUNNING;
            }
        }

        animations.get(currentState).update();
        updateHitbox();
    }

    private void updateHitbox() {
        int offsetX = 28;
        int offsetY = 15;
        hitbox.update(x + offsetX, y + offsetY);
    }

    public void jump() {
        if (!jumping && currentState != State.HIT) {
            jumping = true;           
            velocityY = JUMP_STRENGTH;
        }
    }

    public void onCollision() {
        currentState = State.HIT;
    }

    public void draw(Graphics g) {
        try {
            animations.get(currentState).draw(g, x, y, width, height);
            /* hitbox.draw(g); */
        } catch (Exception e) {
            g.setColor(Color.BLACK);         
            g.fillRect(x, y, width, height);
        }
    }

    public Hitbox geHitbox(){
        return this.hitbox;
    }

    public State getCurrentState(){
        return this.currentState;
    }
}
