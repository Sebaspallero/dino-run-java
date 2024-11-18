package game.manager;

import java.util.List;

import game.entities.Dinosaur;
import game.entities.Obstacle;

public class CollissionManager {
    
    public boolean checkCollision(Dinosaur dinosaur, List<Obstacle> obstacles) {
            for (Obstacle obstacle : obstacles) {
                if (dinosaur.getHitbox().intersects(obstacle.getHitbox())) {
                    return true;
                }
            }
        return false;
    }
}
