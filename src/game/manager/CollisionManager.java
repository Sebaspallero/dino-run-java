package game.manager;

import java.util.List;

import game.entities.character.Dinosaur;
import game.entities.obstacles.AbstractObstacle;
import game.utils.SoundPlayer;

public class CollisionManager {

    private SoundPlayer soundPlayer;
    private LivesManager livesManager;

    private long hitStartTime;
    private final long HIT_DURATION = 400;


    public CollisionManager(SoundPlayer soundPlayer, LivesManager livesManager) {
        this.soundPlayer = soundPlayer;
        this.livesManager = livesManager;
        this.hitStartTime = 0;
    }

      // Check if character has been hit
      public boolean isDinosaurHit(Dinosaur dinosaur) {
        if (dinosaur.getCurrentState() == Dinosaur.State.HIT) {
            long elapsedTime = System.currentTimeMillis() - hitStartTime;
            return elapsedTime >= HIT_DURATION;
        }
        return false;
    }
    
     public void handleCollisions(Dinosaur dinosaur, List<AbstractObstacle> obstacleList) {
        boolean collisionDetected = false;

        for (int i = 0; i < obstacleList.size(); i++) {
            AbstractObstacle obstacle = obstacleList.get(i);

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
            dinosaur.setCollided(false);
        }
    }

    public boolean checkCollision(Dinosaur dinosaur, List<AbstractObstacle> obstacles) {
            for (AbstractObstacle obstacle : obstacles) {
                if (dinosaur.getHitbox().intersects(obstacle.getHitbox())) {
                    return true;
                }
            }
        return false;
    }

     // Handle consecuences of collision (sound, update lives)
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
