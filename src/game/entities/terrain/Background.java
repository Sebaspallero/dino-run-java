package game.entities.terrain;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Background {

    private Image image;
    private int imageWidth;
    private int imageHeight;
    private int offsetY;

    public Background() {
        this.offsetY = 0;

        try {
            image = ImageIO.read(getClass().getResource("/resources/sprites/background-001.png"));
            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update the vertical offset for upward scrolling
    public void update(double deltaTime, int scrollSpeed) {
        offsetY -= deltaTime * scrollSpeed;

        // Reset offsetY to avoid overflow and create seamless looping
        if (offsetY <= -imageHeight) {
            offsetY += imageHeight;
        }
    }

    // Draw the background, repeating vertically
    public void draw(Graphics g, int screenWidth, int screenHeight) {
        if (image == null) {
            return; // No image loaded
        }

        // Start drawing at offsetY to ensure smooth scrolling
        for (int x = 0; x < screenWidth; x += imageWidth) {
            for (int y = offsetY; y < screenHeight; y += imageHeight) {
                g.drawImage(image, x, y, imageWidth, imageHeight, null);
            }
        }
    }
}