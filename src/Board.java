import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Board {
    private JFrame frame;
    private JLayeredPane panel;
    private BufferedImage boardImage;
    private JLabel boardLabel;


    public Board(JFrame frame) {
        this.frame = frame;
        drawBoard();
        Piece piece = new Piece(panel, boardLabel);
    }

    public void drawBoard() {
        panel = new JLayeredPane();
        panel.setPreferredSize(new Dimension(800, 800));
        panel.setLayout(null);
        try {
            boardImage = ImageIO.read(new File("src/Pieces/Chessboard480.svg.png"));
            Image image = boardImage.getScaledInstance(800, 800, Image.SCALE_SMOOTH);
            boardLabel = new JLabel(new ImageIcon(image));
            boardLabel.setBounds(0, 0, 800, 800);
            panel.add(boardLabel);

        }catch (IOException e) {
            e.printStackTrace();
        }
        frame.add(panel);
        frame.pack();
    }
}
