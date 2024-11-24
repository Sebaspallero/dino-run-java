package game.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class TextGenerator {

    private String text;
    private int x;
    private int y;
    private Font font;
    private Color color;


    public TextGenerator(String text, int x, int y, Font font, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
        this.color = color;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.setFont(font);

        g.drawString(text, x, y);
    }
}
