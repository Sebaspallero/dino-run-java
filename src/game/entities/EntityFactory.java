package game.entities;

import game.entities.items.*;
import game.entities.obstacles.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityFactory {

    private static final Random RANDOM = new Random();

    // Método para crear ítems
    public static List<AbstractItem> createItems() {
        List<AbstractItem> items = new ArrayList<>();
        int patternType = RANDOM.nextInt(2); // 0 = línea, 1 = triángulo

        switch (patternType) {
            case 0:
                items.addAll(generateLinePattern()); // Genera un patrón en línea
                break;
            case 1:
                items.addAll(generateTrianglePattern()); // Genera un patrón triangular
                break;
            default:
                throw new IllegalStateException("Patrón de ítems no válido");
        }

        return items;
    }

    // Método para crear obstáculos
    public static List<AbstractObstacle> createObstacles() {
        List<AbstractObstacle> obstacles = new ArrayList<>();
        int obstacleType = RANDOM.nextInt(2); // 0 = Spike, 1 = Bird

        switch (obstacleType) {
            case 0:
                obstacles.add(new Spike());
                break;
            case 1:
                obstacles.add(new Bird());
                break;
            default:
                throw new IllegalStateException("Tipo de obstáculo no válido");
        }

        return obstacles;
    }

    // Genera un patrón en línea de cerezas
    private static List<AbstractItem> generateLinePattern() {
        List<AbstractItem> items = new ArrayList<>();
        int randomAmount = RANDOM.nextInt(5) + 1;
        for (int i = 0; i < randomAmount; i++) {
            items.add(new Cherry(800 + i * 50, 170));
        }
        return items;
    }

    // Genera un patrón triangular de cerezas
    private static List<AbstractItem> generateTrianglePattern() {
        List<AbstractItem> items = new ArrayList<>();
        items.add(new Cherry(800, 170)); // Inferior
        items.add(new Cherry(750, 120)); // Izquierda
        items.add(new Cherry(850, 120)); // Derecha
        items.add(new Cherry(800, 70));  // Superior
        return items;
    }
}