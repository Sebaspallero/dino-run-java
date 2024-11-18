package game.entities;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Background {

    private Image image;   
    private int imageWidth; 
    private int imageHeight; 
    private int offsetX; 

    public Background() {
        this.offsetX = 0;

        try {
            image = ImageIO.read(getClass().getResource("/resources/sprites/background-001.png"));
            imageWidth = image.getWidth(null); 
            imageHeight = image.getHeight(null); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Dibujar el fondo repetitivo
    public void draw(Graphics g, int screenWidth, int screenHeight) {
        if (image == null) {
            return; // No hay imagen cargada
        }

        // Dibuja la imagen en mosaico para cubrir toda la pantalla
        for (int x = offsetX; x < screenWidth; x += imageWidth) {
            for (int y = 0; y < screenHeight; y += imageHeight) {
                g.drawImage(image, x, y, imageWidth, imageHeight, null);
            }
        }
    }
}