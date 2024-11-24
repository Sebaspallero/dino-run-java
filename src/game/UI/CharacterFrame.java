package game.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

public class CharacterFrame {

    private int width;
    private int height;
    private int x;
    private int y;
    private Image image;

    public CharacterFrame() {
        this.width = 48;
        this.height = 48;
        this.x = 10;
        this.y = 10;

        initializaImage();
    }

    public void initializaImage() {
        try {
            image = ImageIO.read(getClass().getResource("/resources/sprites/character-frame.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        try {
            g.drawImage(image, x, y, width, height, null);
        } catch (Exception e) {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, width, height);
        }
    }

}
