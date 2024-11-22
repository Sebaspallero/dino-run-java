package game.manager;


import java.util.ArrayList;
import java.util.List;

import game.UI.Heart;
import game.entities.character.Dinosaur;

public class LivesManager {

    private List <Heart> hearts = new ArrayList<>();
    private final int FIRST = 65;
    private final int SECOND = 97;
    private final int THIRD = 129;

    public LivesManager() {
        hearts.add(new Heart(FIRST, true));
        hearts.add(new Heart(SECOND, true));
        hearts.add(new Heart(THIRD, true));
    }

    public void updateHeart(Dinosaur dinosaur){
        if (dinosaur.hasCollided()) {
            for (int i = 2; i >= 0; i--) {
                if (hearts.get(i).isFull()) {
                    hearts.get(i).setFull(false);
                    break;
                }
            }
        }
    }

    public boolean checkHearts(){
        if (hearts.get(0).isFull()) {
            return false;
        } else {
            return true;
        }
    }

    public void resetHearts(){
        hearts.clear();
        
        hearts.add(new Heart(FIRST, true));
        hearts.add(new Heart(SECOND, true));
        hearts.add(new Heart(THIRD, true));
    }

    public List <Heart> getHearts() {
        return hearts;
    }
    
}
