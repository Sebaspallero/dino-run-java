package game.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.entities.obstacles.Obstacle;

public class ObstacleManager {

    private List<Obstacle> obstacleList;
    private long lastObstacleTime;
    private long obstacleInterval;
    private long initialObstacleInterval;
    private long obstacleIncreaseInterval;
    private long lastDifficultyIncreaseTime;

    // Constructor del manager de obstáculos
    public ObstacleManager() {
        this.obstacleList = new ArrayList<>();
        this.lastObstacleTime = System.currentTimeMillis();
        this.initialObstacleInterval = 2500;  // Intervalo inicial de generación de obstáculos en milisegundos
        this.obstacleInterval = initialObstacleInterval;
        this.obstacleIncreaseInterval = 15000; // Intervalo en el que se reduce la distancia entre obstáculos (10 segundos)
        this.lastDifficultyIncreaseTime = System.currentTimeMillis();
    }

    // Método para actualizar los obstáculos, generar nuevos y manejar su dificultad
    public void update(double deltaTime, int currentSpeed) {
        long currentTime = System.currentTimeMillis();

        // Generación de nuevos obstáculos si ha pasado el intervalo
        if (currentTime - lastObstacleTime >= obstacleInterval) {
            obstacleList.add(new Obstacle(48, 48));  // Crea un nuevo obstáculo de tamaño 48x48
            lastObstacleTime = currentTime;
        }

        // Actualización de los obstáculos existentes y eliminación de los que están fuera de pantalla
        Iterator<Obstacle> iterator = obstacleList.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.update(deltaTime, currentSpeed);  // Actualiza cada obstáculo
            if (obstacle.isOutOfScreen()) {
                iterator.remove();  // Elimina de manera segura los obstáculos fuera de pantalla
            }
        }

        // Incrementar la dificultad: disminuir la distancia entre obstáculos
        increaseDifficulty();
    }

    // Método para aumentar la dificultad del juego
    private void increaseDifficulty() {
        long currentTime = System.currentTimeMillis();
        
        // Si ha pasado el tiempo de intervalo para aumentar la dificultad
        if (currentTime - lastDifficultyIncreaseTime >= obstacleIncreaseInterval) {
            // Reducir el intervalo de generación de obstáculos para aumentar la dificultad
            obstacleInterval = Math.max(obstacleInterval - 250, 550); // Limitar el intervalo a un mínimo de 700 ms
            lastDifficultyIncreaseTime = currentTime;  // Reiniciar el temporizador de aumento de dificultad
        }
    }

    // Método para restablecer todos los obstáculos (al reiniciar el juego, por ejemplo)
    public void resetObstacles() {
        obstacleList.clear();
        this.obstacleInterval = initialObstacleInterval;  // Restaurar el intervalo inicial de generación
        this.lastObstacleTime = System.currentTimeMillis();  // Reiniciar el temporizador
    }

    // Método para obtener la lista de obstáculos
    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }
    
    // Método para cambiar manualmente el intervalo de generación de obstáculos (si se necesita en el futuro)
    public void setObstacleInterval(long newInterval) {
        this.obstacleInterval = newInterval;
    }
}