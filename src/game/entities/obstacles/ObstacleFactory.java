package game.entities.obstacles;
import java.util.Random;

public class ObstacleFactory {

    private static final Random RANDOM = new Random();

    public static AbstractObstacle createObstacle() {
        int obstacleType = RANDOM.nextInt(2);
        
        switch (obstacleType) {
            case 0:
                return new Spike(); 
            case 1:
                return new Bird();
            default:
                throw new IllegalStateException("Tipo de obstáculo no válido");
        }
    }
}
