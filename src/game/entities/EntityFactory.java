package game.entities;

import game.entities.items.*;
import game.entities.obstacles.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityFactory {

    private static final Random RANDOM = new Random();

    public static List<AbstractEntity> createEntity() {
        List<AbstractEntity> entities = new ArrayList<>();
        int entityType = RANDOM.nextInt(6);

        switch (entityType) {
            case 0:
                entities.add(new Bird());
                break;
            case 1:
                entities.add(new Spike(800));
                break;
            case 2:
                entities.addAll(generateLinePattern());
                break;
            case 3:
                entities.addAll(generateTrianglePattern());
                break;
            case 4:
                entities.add(new Pig());
                break;
            case 5:
                entities.add(new Bee(800, 75, 180));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + entityType);
        }

        return entities;
    }

    // Random line pattern
    private static List<AbstractItem> generateLinePattern() {
        List<AbstractItem> items = new ArrayList<>();
        int randomAmount = RANDOM.nextInt(5) + 1;
        for (int i = 0; i < randomAmount; i++) {
            items.add(new Cherry(800 + i * 50, 200));
        }
        return items;
    }

    // Triangle pattern
    private static List<AbstractItem> generateTrianglePattern() {
        List<AbstractItem> items = new ArrayList<>();
        items.add(new Cherry(800, 200));// bottom
        items.add(new Cherry(750, 150));// left
        items.add(new Cherry(850, 150));// right
        items.add(new Cherry(800, 100));// top
        return items;
    }

    // Arrow pattern

    //private static List<AbstractItem> generateArrowPattern() {
    //    List<AbstractItem> items = new ArrayList<>();
    //    items.add(new Cherry(800, 150));// Body left
    //    items.add(new Cherry(850, 150));// Body mid
    //    items.add(new Cherry(900, 150));// Body right
    //    items.add(new Cherry(950, 200));// lower arrow
    //    items.add(new Cherry(950, 100));// upper arrow
    //    items.add(new Cherry(1000, 150));// right arrow
    //    return items;
    // }
 
    //private static List<AbstractObstacle> generateSpikesLines() {
    //    List<AbstractObstacle> spikes = new ArrayList<>();
    //    int randomAmount = RANDOM.nextInt(3) + 1;
    //    for (int i = 0; i < randomAmount; i++) {
    //        spikes.add(new Spike(800 + i * 200));
    //    }
    //    return spikes;
    // } 

}