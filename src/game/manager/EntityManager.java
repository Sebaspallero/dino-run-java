package game.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.entities.AbstractEntity;
import game.entities.EntityFactory;
import game.entities.character.Character;
import game.main.GamePanel;

public class EntityManager {

    private List<AbstractEntity> entityList; // List of entities

    private long lastEntityTime; // Time when the last obstacle was generated
    private long entityInterval; // Interval for generating obstacles
    private long initialEntityInterval; // Initial interval for generating obstacles
    private long entityIncreaseInterval; // Interval for increasing the difficulty of obstacles
    private long lastEntityDifficultyIncreaseTime; // Last time the difficulty of obstacles was adjusted

    public EntityManager(GamePanel gp, Character character) {
        this.entityList = new ArrayList<>();

        this.lastEntityTime = System.currentTimeMillis();
        this.initialEntityInterval = 2000;
        this.entityInterval = initialEntityInterval;
        this.entityIncreaseInterval = 15000;
        this.lastEntityDifficultyIncreaseTime = System.currentTimeMillis();
    }

    // Method to update obstacles and items
    public void update(double deltaTime, int currentSpeed, Character character) {
        long currentTime = System.currentTimeMillis();

        // Generate new obstacles if the interval has passed
        if (currentTime - lastEntityTime >= entityInterval) {
            entityList.addAll(EntityFactory.createEntity());
            lastEntityTime = currentTime;
        }

        // Update obstacles
        Iterator<AbstractEntity> entityIterator = entityList.iterator();
        while (entityIterator.hasNext()) {
            AbstractEntity entity = entityIterator.next();
            entity.update(deltaTime, currentSpeed);
            if (entity.isOutOfScreen()) {
                entityIterator.remove();
            }
        }

        // Increase the difficulty of obstacles and items
        increaseDifficulty();
    }

    // Method to increase the difficulty of obstacles and items
    private void increaseDifficulty() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastEntityDifficultyIncreaseTime >= entityIncreaseInterval) {
            entityInterval = Math.max(entityInterval - 250, 550);
            lastEntityDifficultyIncreaseTime = currentTime;
        }
    }

    // Method to reset obstacles and items
    public void resetEntities() {
        entityList.clear();
        this.entityInterval = initialEntityInterval;
        this.lastEntityTime = System.currentTimeMillis();
    }

    // Methods to get the lists of obstacles and items
    public List<AbstractEntity> getEntityList() {
        return entityList;
    }
}
