package game.entities.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemFactory {

    private static final Random RANDOM = new Random();

    // Método para generar ítems en patrones
    public static List<AbstractItem> createItems() {
        List<AbstractItem> items = new ArrayList<>();
        int patternType = RANDOM.nextInt(2);

        switch (patternType) {
            case 0 -> items.addAll(generateLinePattern());
            case 1 -> items.addAll(generateTrianglePattern());
            default -> throw new IllegalStateException("Patrón de ítems no válido");
        }

        return items;
    }

    //Line
    private static List<AbstractItem> generateLinePattern() {
        List<AbstractItem> items = new ArrayList<>();
        int randomAmount = RANDOM.nextInt(5) + 1;
        for (int i = 0; i < randomAmount; i++) { 
            items.add(new Cherry(800 + i * 50, 170));
        }
        return items;
    }

    //Triangle
    private static List<AbstractItem> generateTrianglePattern() {
        List<AbstractItem> items = new ArrayList<>();
        items.add(new Cherry(800, 170)); // Bottom
        items.add(new Cherry(750, 120)); // Left
        items.add(new Cherry(850, 120)); // Right
        items.add(new Cherry(800, 70));  // Top
        return items;
    }
}