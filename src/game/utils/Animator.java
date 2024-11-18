package game.utils;
import java.awt.Graphics;
import java.awt.Image;

public class Animator {

    private Image spriteSheet; // La sprite sheet completa
    private int frameWidth, frameHeight; // Dimensiones de cada cuadro
    private int currentFrame = 0; // Índice del cuadro actual
    private int frameCount; // Total de cuadros en la animación
    private long lastFrameTime = 0; // Tiempo del último cambio de cuadro
    private long frameDelay; // Tiempo entre cuadros (en milisegundos)
    private int row; // Fila del sprite sheet para la animación

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
            currentFrame = (currentFrame + 1) % frameCount; // Avanzar al siguiente cuadro
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