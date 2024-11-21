package game.utils;
import java.awt.Graphics;
import java.awt.Image;

public class Animator {

    private Image spriteSheet; 
    private int frameWidth, frameHeight; 
    private int currentFrame = 0; 
    private int frameCount; 
    private long lastFrameTime = 0; 
    private long frameDelay; 
    private int row; 

    public Animator(Image spriteSheet, int frameWidth, int frameHeight, int frameCount, long frameDelay, int row) {
        this.spriteSheet = spriteSheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.frameDelay = frameDelay;
        this.row = row;
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= frameDelay) {
            currentFrame = (currentFrame + 1) % frameCount; // Move to next frame
            lastFrameTime = currentTime;
        }
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        int sourceX1 = currentFrame * frameWidth;
        int sourceY1 = row * frameHeight;
        int sourceX2 = sourceX1 + frameWidth;
        int sourceY2 = sourceY1 + frameHeight;

        g.drawImage(spriteSheet, x, y, x + width, y + height, sourceX1, sourceY1, sourceX2, sourceY2, null);
    }
}