package game.entities;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Background {

    private Image image;   // Imagen pequeña del fondo
    private int imageWidth; // Ancho de la imagen
    private int imageHeight; // Alto de la imagen
    private int offsetX; // Desplazamiento acumulado horizontal

    public Background() {
        this.offsetX = 0;

        try {
            image = ImageIO.read(getClass().getResource("/resources/sprites/background-001.png"));
            imageWidth = image.getWidth(null); // Obtener ancho de la imagen
            imageHeight = image.getHeight(null); // Obtener alto de la imagen
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Actualizar el desplazamiento del fondo
    public void update(int speed) {
        offsetX -= speed; // Mover hacia la izquierda

        // Si el desplazamiento supera el ancho de la imagen, reinícialo
        if (offsetX <= -imageWidth) {
            offsetX += imageWidth;
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