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
    private int loopCount; // number of wanted loops (0 = infinity)
    private int currentLoop = 0; // number of completed cycles

    public Animator(Image spriteSheet, int frameWidth, int frameHeight, int frameCount, long frameDelay) {
        this(spriteSheet, frameWidth, frameHeight, frameCount, frameDelay, 0); // By default infinite
    }

    public Animator(Image spriteSheet, int frameWidth, int frameHeight, int frameCount, long frameDelay, int loopCount) {
        this.spriteSheet = spriteSheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = frameCount;
        this.frameDelay = frameDelay;
        this.loopCount = loopCount; // if its 0 it will repeat for infinity
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= frameDelay) {
            currentFrame++; // Go to next frame

            // if we reach last frame we complete one loop and reset currant frame
            if (currentFrame >= frameCount) {
                currentFrame = 0;
                currentLoop++;

                if (loopCount > 0 && currentLoop >= loopCount) {
                    return;
                }
            }
            lastFrameTime = currentTime;
        }
    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        int sourceX1 = currentFrame * frameWidth;
        int sourceY1 = 0 * frameHeight;
        int sourceX2 = sourceX1 + frameWidth;
        int sourceY2 = sourceY1 + frameHeight;

        g.drawImage(spriteSheet, x, y, x + width, y + height, sourceX1, sourceY1, sourceX2, sourceY2, null);
    }

    public boolean isAnimationComplete() {
        return loopCount > 0 && currentLoop >= loopCount;
    }

    public void reset() {
        currentFrame = 0;
        currentLoop = 0;
    }

    public int getCurrentFrame(){
        return this.currentFrame;
    }
}