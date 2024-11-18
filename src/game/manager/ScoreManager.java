package game.manager;

public class ScoreManager {
    private int score;
    private int scoreTimer;
    private final int SCORE_DELAY = 7;
    
    
    public ScoreManager(){
        this.score = 0;
        this.scoreTimer = 0;
    }

    public void update(){
        scoreTimer++;
        if (scoreTimer > SCORE_DELAY) {
            score++;
            scoreTimer = 0;
        }
    }

    public void resetScore(){
        this.score = 0;
        this.scoreTimer = 0;
    }

    public int getScore(){
        return this.score;
    }

    

}
