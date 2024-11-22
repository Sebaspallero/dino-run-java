package game.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.entities.obstacles.AbstractObstacle;
import game.entities.obstacles.ObstacleFactory;

public class ObstacleManager {

    private List<AbstractObstacle> obstacleList;
    private long lastObstacleTime;
    private long obstacleInterval;
    private long initialObstacleInterval;
    private long obstacleIncreaseInterval;
    private long lastDifficultyIncreaseTime;

    public ObstacleManager() {
        this.obstacleList = new ArrayList<>();
        this.lastObstacleTime = System.currentTimeMillis();
        this.initialObstacleInterval = 2500;  // Initial interval for obstacle generation in milliseconds
        this.obstacleInterval = initialObstacleInterval;
        this.obstacleIncreaseInterval = 15000; // Interval for reducing the distance between obstacles (15 seconds)
        this.lastDifficultyIncreaseTime = System.currentTimeMillis();
    }

    // Method to update obstacles, generate new ones, and manage difficulty
    public void update(double deltaTime, int currentSpeed) {
        long currentTime = System.currentTimeMillis();

        // Generate new obstacles if the interval has passed
        if (currentTime - lastObstacleTime >= obstacleInterval) {
            obstacleList.add(ObstacleFactory.createObstacle());
            lastObstacleTime = currentTime;
        }

        // Update existing obstacles and remove those that are off-screen
        Iterator<AbstractObstacle> iterator = obstacleList.iterator();
        while (iterator.hasNext()) {
            AbstractObstacle obstacle = iterator.next();
            obstacle.update(deltaTime, currentSpeed);  
            if (obstacle.isOutOfScreen()) {
                iterator.remove();
            }
        }

        // Increase difficulty: reduce the distance between obstacles
        increaseDifficulty();
    }

    // Method to increase the game's difficulty
    private void increaseDifficulty() {
        long currentTime = System.currentTimeMillis();
    
        if (currentTime - lastDifficultyIncreaseTime >= obstacleIncreaseInterval) {
            // Reduce the generation interval for obstacles to increase difficulty
            obstacleInterval = Math.max(obstacleInterval - 250, 550); // Limit the interval to a minimum of 550 ms
            lastDifficultyIncreaseTime = currentTime;
        }
    }

    // Method to reset all obstacles
    public void resetObstacles() {
        obstacleList.clear();
        this.obstacleInterval = initialObstacleInterval; 
        this.lastObstacleTime = System.currentTimeMillis();
    }

    public List<AbstractObstacle> getObstacleList() {
        return obstacleList;
    }
}