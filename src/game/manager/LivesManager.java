package game.manager;


import java.util.ArrayList;
import java.util.List;

import game.UI.Heart;
import game.entities.character.Dinosaur;

public class LivesManager {

    private List <Heart> hearts = new ArrayList<>();

    public LivesManager() {
        hearts.add(new Heart(10, true));
        hearts.add(new Heart(42, true));
        hearts.add(new Heart(74, true));
    }

    public void updateHeart(Dinosaur dinosaur){
        if (dinosaur.hasCollided()) {
            for (int i = 0; i < hearts.size(); i++) {
                if (hearts.get(i).isFull()) {
                    hearts.get(i).setFull(false);
                    break;
                }
            }
        }
    }

    public boolean checkHearts(){
        if (hearts.get(2).isFull()) {
            return false;
        } else {
            return true;
        }
    }

    public void resetHearts(){
        hearts.clear();
        
        hearts.add(new Heart(10, true));
        hearts.add(new Heart(42, true));
        hearts.add(new Heart(74, true));
    }

    public List <Heart> getHearts() {
        return hearts;
    }
    
}
