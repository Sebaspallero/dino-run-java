package game.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.entities.EntityFactory;
import game.entities.items.AbstractItem;


public class ItemManager {

    private List<AbstractItem> cherryList;
    private long lastItemTime;
    private long itemInterval; // Intervalo entre la generación de ítems

    public ItemManager() {
        this.cherryList = new ArrayList<>();
        this.lastItemTime = System.currentTimeMillis();
        this.itemInterval = 4000; // Generar ítems cada 5 segundos (ejemplo)
    }

    // Método para actualizar las cerezas
    public void update(double deltaTime, int currentSpeed) {
        long currentTime = System.currentTimeMillis();

        // Generar nuevas cerezas si ha pasado el intervalo
        if (currentTime - lastItemTime >= itemInterval) {
            cherryList.addAll(EntityFactory.createItems());
            lastItemTime = currentTime;
        }

        // Actualizar cerezas existentes y remover las que salen de la pantalla
        Iterator<AbstractItem> iterator = cherryList.iterator();
        while (iterator.hasNext()) {
            AbstractItem cherry = iterator.next();
            cherry.update(deltaTime, currentSpeed); // Reutiliza el método `update` en `AbstractItem`
            if (cherry.isOutOfScreen()) {
                iterator.remove();
            }
        }
    }

    // Método para resetear todas las cerezas
    public void resetItems() {
        cherryList.clear();
        this.lastItemTime = System.currentTimeMillis();
    }

    public List<AbstractItem> getCherryList() {
        return cherryList;
    }
}