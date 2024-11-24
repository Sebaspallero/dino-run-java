package game.main;

import java.awt.*;
import javax.swing.JFrame;

public class Game extends JFrame {

    public static final int SCREEN_WIDTH = 750;
    public static final int SCREEN_HEIGHT = 390;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Run or Croak");
        Image icon = Toolkit.getDefaultToolkit().getImage(Game.class.getResource("/resources/sprites/icon.png"));
        GamePanel gp = new GamePanel();

        frame.add(gp);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setIconImage(icon);

        gp.startGame();
    }

}
