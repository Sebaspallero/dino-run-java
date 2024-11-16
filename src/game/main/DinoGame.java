package game.main;

import javax.swing.JFrame;

public class DinoGame extends JFrame{

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dino Run");
        GamePanel gp = new GamePanel();

        frame.add(gp);
        frame.setSize(750, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        gp.startGame();
    }
    
}
