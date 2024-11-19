package game.entities;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import game.utils.Animator;

public class Dinosaur {

    //Character States
    public enum State {
        RUNNING, 
        CROUCHING, 
        HIT,
        JUMPING,
        FALLING
    }

    //Constants
    private static final int GROUND_Y = 170; // The position of the character on the ground
    private static final double GRAVITY = 1;
    private static final int JUMP_STRENGTH = -17; //How high is the jump
    private static final int HITBOX_OFFSET_X = 28;


    //Attributes
    private int x;
    private int y;
    private int width;
    private int height;

    private int velocityY; 
    private boolean jumping; 
    private boolean crouching;
    private boolean collided;

    private State currentState;
    private Hitbox hitbox;
    private Map<State, Animator> animations;

    //Constructor
    public Dinosaur() {
        this.x = 50; //Position of the dinosaur on the screen horizontally (fixed)
        this.y = GROUND_Y; // Initial Position of the dinosaur on the screen vertically
        this.width = 64; //Width of the dinosaur
        this.height = 64; //Height of the dinosaur
        this.velocityY = 0; //Vertical speed
        this.jumping = false;
        this.crouching = false;
        this.collided = false;
        this.currentState = State.RUNNING;

        this.animations = new HashMap<>();
        this.hitbox = new Hitbox(x, y, (int) (height * 0.6), (int) (width * 0.5));

        initializeAnimations();
    }

     //Load animations for each state
    public void initializeAnimations(){
        try {
            animations.put(State.RUNNING, createAnimator("/resources/sprites/frog-run.png", 2, 12));
            animations.put(State.HIT, createAnimator("/resources/sprites/frog-hit.png", 2, 7));
            animations.put(State.JUMPING, createAnimator("/resources/sprites/frog-jump.png", 2, 1));
            animations.put(State.FALLING, createAnimator("/resources/sprites/frog-fall.png", 2, 1));
            animations.put(State.CROUCHING, createAnimator("/resources/sprites/dinosaur-crouch-sheet.png", 2, 6));

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public Animator createAnimator(String path, int scaleFactor, int frameCount) throws Exception{
        Image spriteSheet = ImageIO.read(getClass().getResource(path));
        return new Animator(spriteSheet, 32 * scaleFactor, 32 * scaleFactor, frameCount, 90,0);
    }

    public void update() {
        if (jumping) {
            handleJump();
        } else if (crouching) {
            currentState = State.CROUCHING;
        } else if (currentState != State.HIT) {
            currentState = State.RUNNING;
        }

        animations.get(currentState).update();
        updateHitbox();
    }

    private void handleJump() {
        velocityY += GRAVITY;
        y += velocityY;

        if (velocityY < 0) {
            currentState = State.JUMPING;
        } else if (velocityY > 0) {
            currentState = State.FALLING;
        }

        if (y >= GROUND_Y) {
            resetToGround();
        }
    }

    private void resetToGround() {
        y = GROUND_Y;
        jumping = false;
        velocityY = 0;
        currentState = State.RUNNING;
    }

    //Character Hitbox
    private void updateHitbox() {
        int offsetY = (currentState == State.CROUCHING) ? 30 : 15;
        hitbox.update(x + HITBOX_OFFSET_X, y + offsetY);
    }

    public void jump() {
        if (!jumping && currentState != State.HIT) {
            jumping = true;           
            velocityY = JUMP_STRENGTH;
        }
    }

    public void crouch(){
        if (!crouching && currentState != State.HIT) {
            crouching = true;
        }
    }

    public void stand(){
        if (crouching && currentState != State.HIT) {
            crouching = false;
        }
    }

    public void onCollision() {
        this.currentState = State.HIT;
        this.collided = true; 
        this.jumping = false; 
        this.velocityY = 0;
        this.y = GROUND_Y;
        this.crouching = false;
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

    public Hitbox getHitbox(){
        return this.hitbox;
    }

    public State getCurrentState(){
        return this.currentState;
    }

    public boolean hasCollided() {
        return collided;
    }
    
    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}
