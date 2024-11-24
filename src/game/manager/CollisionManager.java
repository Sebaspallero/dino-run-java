package game.manager;

import java.util.List;

import game.entities.AbstractEntity;
import game.entities.character.Character;
import game.entities.items.Cherry;
import game.entities.obstacles.AbstractObstacle;
import game.utils.SoundPlayer;

public class CollisionManager {

    private SoundPlayer soundPlayer;
    private LivesManager livesManager;
    private ScoreManager scoreManager;

    private long hitStartTime;
    private final long HIT_DURATION = 200;

    public CollisionManager(SoundPlayer soundPlayer, LivesManager livesManager, ScoreManager scoreManager) {
        this.soundPlayer = soundPlayer;
        this.livesManager = livesManager;
        this.scoreManager = scoreManager;
        this.hitStartTime = 0;
    }

    public void update() {
        scoreManager.update();
    }

    // Check if character has been hit
    public boolean ischaracterHit(Character character) {
        if (character.getCurrentState() == Character.State.HIT) {
            long elapsedTime = System.currentTimeMillis() - hitStartTime;
            return elapsedTime >= HIT_DURATION;
        }
        return false;
    }

    public void handleCollisions(Character character, List<AbstractEntity> entityList) {
        boolean collisionDetected = false;

        for (int i = 0; i < entityList.size(); i++) {
            AbstractEntity entity = entityList.get(i);

            if (entity instanceof AbstractObstacle) {
                if (checkCollision(character, entity)) {
                    collisionDetected = true;
                    if (!character.hasCollided()) {
                        handleCharacterCollision(character);
                    }
                    break;
                }

                // Remove obstacles if they are beyond X=0
                if (entity.isOutOfScreen()) {
                    entityList.remove(i);
                    i--;
                }
            } else if (entity instanceof Cherry) {
                if (checkCollision(character, entity)) {
                    entity.setCurrentState(Cherry.State.COLLECTED); // -FIX- ITEM DELETED BEFORE ANIMATION GOT NO TIME TO RENDER 
                    soundPlayer.setFile(1);
                    soundPlayer.play();
                    scoreManager.addPoints(20);
                    entityList.remove(i);
                    i--;
                }
            }
        }

        if (!collisionDetected && character.hasCollided()) {
            character.setCollided(false);
        }

    }

    public boolean checkCollision(Character character, AbstractEntity entity) {
        return character.getHitbox().intersects(entity.getHitbox());
    }

    private void handleCharacterCollision(Character character) {
        character.onCollision();
        soundPlayer.setFile(2);
        soundPlayer.play();
        hitStartTime = System.currentTimeMillis();
        livesManager.updateHeart(character);
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public void setHitStartTime(int newHitStartTime) {
        this.hitStartTime = newHitStartTime;
    }
}