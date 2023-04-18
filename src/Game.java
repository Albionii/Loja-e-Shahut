import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Loja");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        Board board = new Board(frame);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
