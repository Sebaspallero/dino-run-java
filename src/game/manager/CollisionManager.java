package game.manager;

import java.util.List;
import game.entities.character.Dinosaur;
import game.entities.items.AbstractItem;
import game.entities.items.Cherry;
import game.entities.obstacles.AbstractObstacle;
import game.utils.SoundPlayer;

public class CollisionManager {

    private SoundPlayer soundPlayer;
    private LivesManager livesManager;
    private ScoreManager scoreManager;

    private long hitStartTime;
    private final long HIT_DURATION = 500;

    public CollisionManager(SoundPlayer soundPlayer, LivesManager livesManager, ScoreManager scoreManager) {
        this.soundPlayer = soundPlayer;
        this.livesManager = livesManager;
        this.scoreManager =  scoreManager; // Crear el objeto ScoreManager
        this.hitStartTime = 0;
    }

    // Actualizar puntaje por el paso del tiempo
    public void update() {
        scoreManager.update();
    }

    // Check if character has been hit
    public boolean isDinosaurHit(Dinosaur dinosaur) {
        if (dinosaur.getCurrentState() == Dinosaur.State.HIT) {
            long elapsedTime = System.currentTimeMillis() - hitStartTime;
            return elapsedTime >= HIT_DURATION;
        }
        return false;
    }

    // Manejo de las colisiones con obstáculos
    public void handleObstacleCollisions(Dinosaur dinosaur, List<AbstractObstacle> obstacleList) {
        boolean collisionDetected = false;

        // Colisiones con obstáculos
        for (int i = 0; i < obstacleList.size(); i++) {
            AbstractObstacle obstacle = obstacleList.get(i);

            if (checkCollision(dinosaur, obstacle)) {
                collisionDetected = true;

                if (!dinosaur.hasCollided()) {
                    handleDinosaurCollision(dinosaur);  // Solo afecta la vida por obstáculos
                }
                break;
            }

            // Eliminar obstáculos que salen de la pantalla
            if (obstacle.isOutOfScreen()) {
                obstacleList.remove(i);
                i--;
            }
        }

        // Resetear el estado de colisión del dinosaurio si no hubo colisión
        if (!collisionDetected && dinosaur.hasCollided()) {
            dinosaur.setCollided(false);
        }
    }

   
    // Manejo de las colisiones con cerezas
    public void handleCherryCollisions(Dinosaur dinosaur, List<AbstractItem> itemList) {
        for (int i = 0; i < itemList.size(); i++) {
            AbstractItem item = itemList.get(i);

        // Si el dinosaurio colide con la cereza y la cereza está en estado IDLE
        if (checkItemCollision(dinosaur, item) && item.getCurrentState() == Cherry.State.IDLE) {
            item.setCurrentState(AbstractItem.State.COLLECTED); 
            soundPlayer.setFile(1);
            soundPlayer.play();
            scoreManager.addPoints(20);
            itemList.remove(i);
            i--; 
        }
    }
}

    // Verifica colisión entre el dinosaurio y un obstáculo
    public boolean checkCollision(Dinosaur dinosaur, AbstractObstacle obstacle) {
        return dinosaur.getHitbox().intersects(obstacle.getHitbox());
    }

    // Verifica colisión entre el dinosaurio y una cereza
    public boolean checkItemCollision(Dinosaur dinosaur, AbstractItem item) {
        return dinosaur.getHitbox().intersects(item.getHitbox());
    }

    // Manejo de las consecuencias de la colisión con un obstáculo (sonido, vidas, etc.)
    private void handleDinosaurCollision(Dinosaur dinosaur) {
        dinosaur.onCollision();
        soundPlayer.setFile(2); // Sonido de colisión
        soundPlayer.play();
        hitStartTime = System.currentTimeMillis();
        livesManager.updateHeart(dinosaur); // Actualiza las vidas del dinosaurio
    }

    // Obtener puntaje
    public int getScore() {
        return scoreManager.getScore();
    }

    public void setHitStartTime(int newHitStartTime){
        this.hitStartTime = newHitStartTime;
    }
}