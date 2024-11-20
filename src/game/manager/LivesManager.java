package game.manager;

import game.UI.Heart;
import game.entities.character.Dinosaur;

public class LivesManager {

    private Heart[] heartArray;

    public LivesManager() {
        heartArray = new Heart[3];

        heartArray[0] = new Heart(10, true);
        heartArray[1] = new Heart(42, true);
        heartArray[2] = new Heart(74, true);
    }

    public void updateHeart(Dinosaur dinosaur){
        if (dinosaur.hasCollided()) {
            for (int i = 0; i < heartArray.length; i++) {
                if (heartArray[i].isFull()) {
                    heartArray[i].setFull(false);
                    break;
                }
            }
        }
    }

    public void resetHearts(){
        heartArray = new Heart[3];

        heartArray[0] = new Heart(10, true);
        heartArray[1] = new Heart(42, true);
        heartArray[2] = new Heart(74, true);
    }

    public Heart[] getHearts() {
        return heartArray;
    }

    
}
