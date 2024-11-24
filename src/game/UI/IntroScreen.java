package game.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class IntroScreen {

 public static void drawIntroScreen(Graphics g, Font customBoldFont, int width, int height) {
    g.setColor(new Color(33, 31, 48));
    g.fillRect(0, 0, width, height);

    g.setColor(new Color(255, 255, 255));
    g.setFont(customBoldFont.deriveFont(50f));
    g.drawString("Created by", width / 2 - 150, height / 2);
    g.drawString("Sebastián Pallero Oría", width / 2 - 100, height / 2 + 60);
}
}
