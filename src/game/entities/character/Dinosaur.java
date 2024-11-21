package game.entities.character;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import game.entities.Hitbox;
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
    private static final int HITBOX_OFFSET_X = 15;


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
        this.x = 50;
        this.y = GROUND_Y;
        this.width = 64;
        this.height = 64; 
        this.velocityY = 0; 
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
            animations.put(State.RUNNING, createAnimator("/resources/sprites/frog-run.png", 2, 12,90));
            animations.put(State.HIT, createAnimator("/resources/sprites/frog-hit.png", 2, 7,70));
            animations.put(State.JUMPING, createAnimator("/resources/sprites/frog-jump.png", 2, 1,90));
            animations.put(State.FALLING, createAnimator("/resources/sprites/frog-fall.png", 2, 1,90));
            animations.put(State.CROUCHING, createAnimator("/resources/sprites/frog-roll.png", 2, 5,90));

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public Animator createAnimator(String path, int scaleFactor, int frameCount, int frameDelay) throws Exception{
        Image spriteSheet = ImageIO.read(getClass().getResource(path));
        return new Animator(spriteSheet, 64, 64, frameCount, frameDelay,0);
    }

    //Update States of the character
    public void update() {
        if (currentState != State.HIT) {
            // Solo actualizamos la lógica de salto, caída y otras acciones si no estamos en HIT
            if (jumping) {
                handleJump();
            } else if (crouching) {
                currentState = State.CROUCHING;
            } else {
                currentState = State.RUNNING;
            }
        }
    
        // Si está en estado HIT, solo actualizamos la animación
        animations.get(currentState).update();
    
        updateHitbox();  // Actualizamos el hitbox independientemente del estado
    }

    private void handleJump() {
        velocityY += GRAVITY;  // La gravedad sigue afectando al personaje
        y += velocityY;        // Actualizamos la posición Y según la velocidad vertical

        if (velocityY < 0) {
            currentState = State.JUMPING;  // Si el personaje está subiendo, se mantiene en el estado de salto
        } else if (velocityY > 0) {
            currentState = State.FALLING;  // Si el personaje está cayendo, se mantiene en el estado de caída
        }

        // Si llega al suelo, vuelve al estado RUNNING
        if (y >= GROUND_Y) {
            resetToGround();
        }
    }

    private void resetToGround() {
        y = GROUND_Y;        // Coloca al dinosaurio en el suelo
        jumping = false;     // Detiene el salto
        velocityY = 0;       // Detenemos la velocidad vertical
        currentState = State.RUNNING;  // Cambia al estado de correr
    }

    //Character Hitbox
    private void updateHitbox() {
        int offsetY = (currentState == State.CROUCHING) ? 30 : 15;
        hitbox.update(x + HITBOX_OFFSET_X, y + offsetY);
    }

    //Actions
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
        this.currentState = State.HIT;  // Cambia solo el estado para la animación de colisión
        this.collided = true;  // Marca que el dinosaurio ha colisionado
    
        // No modificamos la posición o la velocidad. La física sigue intacta.
          // Detiene el salto si estaba saltando, pero sin afectar la gravedad  // Detenemos el movimiento vertical solo temporalmente, para la animación de colisión
        this.crouching = false; // Asegura que el dinosaurio no permanezca agachado durante la colisión
    }

    //Draw the character
    public void draw(Graphics g) {
        try {
            animations.get(currentState).draw(g, x, y, width, height);
            /* hitbox.draw(g); */ //Show hitbox
        } catch (Exception e) {
            g.setColor(Color.BLACK);         
            g.fillRect(x, y, width, height);
        }
    }

    //Getters and Setters
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

    public void setCurrentState(State newState){
        this.currentState = newState;
    }
}
