package game.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import game.entities.terrain.Background;
import game.entities.terrain.Floor;
import game.entities.character.Character;
import game.main.GamePanel;

public class TitleScreen {

    public static void drawTitleScreen(Graphics g, Font customFont, int width, int height, GamePanel gp, Background background, Floor floor, Character character) {
        ImageIcon titleGif = new ImageIcon(GamePanel.class.getResource("/resources/sprites/title-screen.gif"));

        background.draw(g, width, height);
        floor.draw(g);
        character.draw(g);

        if (titleGif != null) {
            titleGif.paintIcon(gp, g, width / 2 - 150, -30);
        }

        g.setFont(customFont.deriveFont(30f));
        g.setColor(new Color(33, 31, 48));
        g.drawString("Press SPACE to start", width / 2 - 75, 260);
    }
}
