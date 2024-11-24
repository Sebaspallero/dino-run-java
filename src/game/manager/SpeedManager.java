package game.manager;

public class SpeedManager {

    private int currentSpeed;
    private long lastSpeedIncreaseTime;
    private long speedIncreaseInterval;

    public SpeedManager(int initialSpeed, long speedIncreaseInterval) {
        this.currentSpeed = initialSpeed;
        this.speedIncreaseInterval = speedIncreaseInterval;
        this.lastSpeedIncreaseTime = System.currentTimeMillis();
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpeedIncreaseTime >= speedIncreaseInterval) {
            currentSpeed += 50;
            lastSpeedIncreaseTime = currentTime;
        }
    }

    public void resetSpeed(int initialSpeed) {
        this.currentSpeed = initialSpeed;
        this.lastSpeedIncreaseTime = System.currentTimeMillis();
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

}
