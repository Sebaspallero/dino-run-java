package game.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.entities.Obstacle;

public class ObstacleManager {

    private List<Obstacle> obstacleList;

    private long lastObstacleTime;
    private long obstacleInterval;
    private long lastDifficultyUpdateTime;
    private long obstacleIncreaseInterval;

    public ObstacleManager(long initialInterval, long obstacleIncreaseInterval) {
        this.obstacleList = new ArrayList<>();
        this.lastObstacleTime = System.currentTimeMillis();
        this.obstacleInterval = initialInterval;
        this.lastDifficultyUpdateTime = System.currentTimeMillis();
        this.obstacleIncreaseInterval = obstacleIncreaseInterval;
    }

    public void update(double deltaTime, int currentSpeed) {
        long currentTime = System.currentTimeMillis();

        // Ajuste de dificultad: reduce el intervalo de aparición de obstáculos
        if (currentTime - lastDifficultyUpdateTime >= obstacleIncreaseInterval) {
            obstacleInterval = Math.max(500, 2000 - (currentSpeed / 3));  // Ajuste más dinámico
            lastDifficultyUpdateTime = currentTime;
        }

        // Generación de nuevos obstáculos
        if (currentTime - lastObstacleTime >= obstacleInterval) {
            obstacleList.add(new Obstacle(48, 48)); // Tamaño fijo de obstáculos
            lastObstacleTime = currentTime;
        }

         // Actualización de los obstáculos existentes y eliminación de los que están fuera de pantalla
         Iterator<Obstacle> iterator = obstacleList.iterator();
         
         while (iterator.hasNext()) {
             Obstacle obstacle = iterator.next();
             obstacle.update(deltaTime, currentSpeed);
             if (obstacle.isOutOfScreen()) {
                 iterator.remove(); // Eliminar de manera segura mientras se itera
             }
         }
    }

    public void resetObstacles(){
        obstacleList.clear();
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }
}
