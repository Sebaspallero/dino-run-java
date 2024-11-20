package game.manager;

import java.util.List;

import game.entities.character.Dinosaur;
import game.entities.obstacles.Obstacle;
import game.utils.SoundPlayer;

public class CollisionManager {

    private SoundPlayer soundPlayer;
    private LivesManager livesManager;

    private long hitStartTime;
    private final long HIT_DURATION = 1000;

    public CollisionManager(SoundPlayer soundPlayer, LivesManager livesManager) {
        this.soundPlayer = soundPlayer;
        this.livesManager = livesManager;
        this.hitStartTime = 0;
    }

    // Verifica si el dinosaurio ha sido golpeado
    public boolean isDinosaurHit(Dinosaur dinosaur) {
        if (dinosaur.getCurrentState() == Dinosaur.State.HIT) {
            long elapsedTime = System.currentTimeMillis() - hitStartTime;
            return elapsedTime >= HIT_DURATION;
        }
        return false;
    }
    
     // Maneja las colisiones entre el dinosaurio y los obstáculos
     public void handleCollisions(Dinosaur dinosaur, List<Obstacle> obstacleList) {
        boolean collisionDetected = false;

        for (int i = 0; i < obstacleList.size(); i++) {
            Obstacle obstacle = obstacleList.get(i);

            if (checkCollision(dinosaur, obstacleList)) {
                collisionDetected = true;

                if (!dinosaur.hasCollided()) {
                    handleDinosaurCollision(dinosaur);
                }
                break;
            }

            if (obstacle.isOutOfScreen()) {
                obstacleList.remove(i);
                i--;
            }
        }

        if (!collisionDetected && dinosaur.hasCollided()) {
            dinosaur.setCollided(false); // Restablece el estado del dinosaurio
        }
    }

    public boolean checkCollision(Dinosaur dinosaur, List<Obstacle> obstacles) {
            for (Obstacle obstacle : obstacles) {
                if (dinosaur.getHitbox().intersects(obstacle.getHitbox())) {
                    return true;
                }
            }
        return false;
    }

     // Maneja las consecuencias de una colisión (por ejemplo, el sonido y la actualización de vidas)
     private void handleDinosaurCollision(Dinosaur dinosaur) {
        dinosaur.onCollision();
        soundPlayer.setFile(2);
        soundPlayer.play();
        hitStartTime = System.currentTimeMillis();
        livesManager.updateHeart(dinosaur);
    }

    public void setHitStartTime(int newHitStartTime){
         this.hitStartTime = newHitStartTime;
    }
}
