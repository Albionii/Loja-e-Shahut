import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Piece {
    private JLayeredPane panel;
    private BufferedImage[][] blackPieces = new BufferedImage[2][8];
    private JLabel [][] labelBlackPieces = new JLabel[2][8];
    private BufferedImage[][] whitePieces = new BufferedImage[2][8];
    private JLabel [][] labelWhitePieces = new JLabel[2][8];
    private JLabel boardLabel;
    private SavePosition[][] savePositionForWhite = new SavePosition[2][8];
    private SavePosition[][] savePositionForBlack = new SavePosition[2][8];

    public Piece(JLayeredPane panel, JLabel boardLabel) {
        this.panel = panel;
        this.boardLabel = boardLabel;
        drawPieces();
        Move move = new Move(labelWhitePieces, labelBlackPieces, boardLabel, savePositionForWhite, savePositionForBlack);
    }

    public void drawPieces() {
        try {

            blackPieces[0][0] = ImageIO.read(new File("src/Pieces/Black/Chess_rdt45.svg.png"));
            blackPieces[0][1] = ImageIO.read(new File("src/Pieces/Black/Chess_ndt45.svg.png"));
            blackPieces[0][2] = ImageIO.read(new File("src/Pieces/Black/Chess_bdt45.svg.png"));
            blackPieces[0][3] = ImageIO.read(new File("src/Pieces/Black/Chess_qdt45.svg.png"));
            blackPieces[0][4] = ImageIO.read(new File("src/Pieces/Black/Chess_kdt45.svg.png"));
            blackPieces[0][5] = ImageIO.read(new File("src/Pieces/Black/Chess_bdt45.svg.png"));
            blackPieces[0][6] = ImageIO.read(new File("src/Pieces/Black/Chess_ndt45.svg.png"));
            blackPieces[0][7] = ImageIO.read(new File("src/Pieces/Black/Chess_rdt45.svg.png"));
            blackPieces[1][0] = ImageIO.read(new File("src/Pieces/Black/Chess_pdt45.svg.png"));
            blackPieces[1][1] = ImageIO.read(new File("src/Pieces/Black/Chess_pdt45.svg.png"));
            blackPieces[1][2] = ImageIO.read(new File("src/Pieces/Black/Chess_pdt45.svg.png"));
            blackPieces[1][3] = ImageIO.read(new File("src/Pieces/Black/Chess_pdt45.svg.png"));
            blackPieces[1][4] = ImageIO.read(new File("src/Pieces/Black/Chess_pdt45.svg.png"));
            blackPieces[1][5] = ImageIO.read(new File("src/Pieces/Black/Chess_pdt45.svg.png"));
            blackPieces[1][6] = ImageIO.read(new File("src/Pieces/Black/Chess_pdt45.svg.png"));
            blackPieces[1][7] = ImageIO.read(new File("src/Pieces/Black/Chess_pdt45.svg.png"));

            whitePieces[0][0] = ImageIO.read(new File("src/Pieces/White/Chess_rlt45.svg.png"));
            whitePieces[0][1] = ImageIO.read(new File("src/Pieces/White/Chess_nlt45.svg.png"));
            whitePieces[0][2] = ImageIO.read(new File("src/Pieces/White/Chess_blt45.svg.png"));
            whitePieces[0][3] = ImageIO.read(new File("src/Pieces/White/Chess_qlt45.svg.png"));
            whitePieces[0][4] = ImageIO.read(new File("src/Pieces/White/Chess_klt45.svg.png"));
            whitePieces[0][5] = ImageIO.read(new File("src/Pieces/White/Chess_blt45.svg.png"));
            whitePieces[0][6] = ImageIO.read(new File("src/Pieces/White/Chess_nlt45.svg.png"));
            whitePieces[0][7] = ImageIO.read(new File("src/Pieces/White/Chess_rlt45.svg.png"));
            whitePieces[1][0] = ImageIO.read(new File("src/Pieces/White/Chess_plt45.svg.png"));
            whitePieces[1][1] = ImageIO.read(new File("src/Pieces/White/Chess_plt45.svg.png"));
            whitePieces[1][2] = ImageIO.read(new File("src/Pieces/White/Chess_plt45.svg.png"));
            whitePieces[1][3] = ImageIO.read(new File("src/Pieces/White/Chess_plt45.svg.png"));
            whitePieces[1][4] = ImageIO.read(new File("src/Pieces/White/Chess_plt45.svg.png"));
            whitePieces[1][5] = ImageIO.read(new File("src/Pieces/White/Chess_plt45.svg.png"));
            whitePieces[1][6] = ImageIO.read(new File("src/Pieces/White/Chess_plt45.svg.png"));
            whitePieces[1][7] = ImageIO.read(new File("src/Pieces/White/Chess_plt45.svg.png"));




            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 8; j++) {
                    Image image = blackPieces[i][j].getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    labelBlackPieces[i][j] = new JLabel(new ImageIcon(image));
                    labelBlackPieces[i][j].setBounds(j*100, i*100, 100, 100);
                    Image image1 = whitePieces[i][j].getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    labelWhitePieces[i][j] = new JLabel(new ImageIcon(image1));
                    labelWhitePieces[i][j].setBounds(j*100, (7-i)*100, 100, 100);
                    boardLabel.add(labelWhitePieces[i][j]);
                    boardLabel.add(labelBlackPieces[i][j]);
                    savePositionForBlack[i][j] = new SavePosition(j, i, j, i, 'b');
                    savePositionForWhite[i][j] = new SavePosition(j, i, j, 7-i, 'w');
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
