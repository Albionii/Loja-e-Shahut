import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.Time;

public class Move implements MouseListener, MouseMotionListener {

    private JLabel[][] labelWhitePieces;
    private JLabel[][] labelBlackPieces;
    private JLabel boardLabel;
    private boolean clickedBlack, clickedWhite, mousePressed;
    private int xPosition, yPosition;
    private int uB, vB, uW, vW;
    private String[][] eachPieceName = new String[2][8];
    private SavePosition[][] savePositionForWhite,savePositionForBlack;
    private char bOrw;
    int xRelease, yRelease;

    public Move(JLabel[][] labelWhitePieces, JLabel[][] labelBlackPieces, JLabel boardLabel, SavePosition[][] savePositionForWhite, SavePosition[][] savePositionForBlack) {
        this.labelWhitePieces = labelWhitePieces;
        this.labelBlackPieces = labelBlackPieces;
        this.boardLabel = boardLabel;
        this.savePositionForWhite = savePositionForWhite;
        this.savePositionForBlack = savePositionForBlack;
        addListenerToLabels();
        setPieceName();
    }

    public void addListenerToLabels() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                labelBlackPieces[i][j].addMouseListener(this);
                labelWhitePieces[i][j].addMouseListener(this);;
                labelBlackPieces[i][j].addMouseMotionListener(this);
                labelWhitePieces[i][j].addMouseMotionListener(this);
            }
        }
        boardLabel.addMouseListener(this);
    }

    public void getPositionOfPiece(int xPos, int yPos) {
        xPosition = xPos / 100;
        yPosition = yPos / 100;
    }

    public void setPieceName() {
        eachPieceName[0][0] = "R";
        eachPieceName[0][1] = "N";
        eachPieceName[0][2] = "B";
        eachPieceName[0][3] = "Q";
        eachPieceName[0][4] = "K";
        eachPieceName[0][5] = "B";
        eachPieceName[0][6] = "N";
        eachPieceName[0][7] = "R";
        for (int j = 0; j < 8; j++) {
            eachPieceName[1][j] = "P";
        }
    }

    public void restartPreviousMove() {
        if (clickedBlack){
            labelBlackPieces[uB][vB].setBounds(savePositionForBlack[uB][vB].getXPosition()*100, savePositionForBlack[uB][vB].getYPosition()*100, 100, 100);
            clickedBlack = false;
            clickedWhite = true;
        }else if (clickedWhite) {
            labelWhitePieces[uW][vW].setBounds(savePositionForWhite[uW][vW].getXPosition()*100, savePositionForWhite[uW][vW].getYPosition()*100, 100, 100);
            clickedWhite = false;
            clickedBlack = true;
        }
    }


    public void pawn() {

        if (xPosition == savePositionForBlack[uB][vB].getXPosition() && yPosition == (savePositionForBlack[uB][vB].getYPosition()+1) && clickedBlack) {
            labelBlackPieces[uB][vB].setBounds(xPosition*100, yPosition*100, 100, 100);
            labelBlackPieces[uB][vB].setOpaque(false);
            savePositionForBlack[uB][vB].setXPosition(xPosition);
            savePositionForBlack[uB][vB].setYPosition(yPosition);
            clickedWhite = false;
            clickedBlack = true;
        }
        else if (xPosition == savePositionForWhite[uW][vW].getXPosition() && yPosition == ((savePositionForWhite[uW][vW].getYPosition())-1) && clickedWhite) {
            labelWhitePieces[uW][vW].setBounds(xPosition*100, yPosition*100, 100, 100);
            labelWhitePieces[uW][vW].setOpaque(false);
            savePositionForWhite[uW][vW].setXPosition(xPosition);
            savePositionForWhite[uW][vW].setYPosition(yPosition);
            clickedBlack = false;
            clickedWhite = true;

        }
        else {
            restartPreviousMove();
            System.out.println("clickedWhite : " + clickedWhite);
        }
    }

    public void isThereAPiece(int xPos, int yPos) {
        for (int i = 0; i < 2; i++ ) {
            for (int j = 0; j < 8; j++) {
                if (labelBlackPieces[i][j].getX()/100 == xPos && labelBlackPieces[i][j].getX()/100 == yPos) {
                    System.out.println("Eshte nje figure qaty");
                    labelBlackPieces[i][j].setOpaque(true);
                    labelBlackPieces[i][j].setBackground(Color.green);
                }
            }
        }

    }





    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 8; j++) {
                if (e.getSource() == labelBlackPieces[i][j] && !clickedBlack){
                    mousePressed = true;
                    clickedBlack = true;
                    clickedWhite = false;
                    labelBlackPieces[i][j].setOpaque(false);
                }
                if (e.getSource() == labelWhitePieces[i][j] && !clickedWhite){
                    mousePressed = true;
                    clickedWhite = true;
                    clickedBlack = false;
                    labelWhitePieces[i][j].setOpaque(false);
                }

            }
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        if (mousePressed) {
            for (int i = 0; i < 2; i++){
                for (int j = 0; j < 8; j++) {
                    if (e.getSource() == labelBlackPieces[i][j]){
                        Point mouse = boardLabel.getMousePosition();
                        labelBlackPieces[i][j].setLocation(mouse.x - 50, mouse.y - 50);
                        xRelease = (int) Math.round((mouse.x-50)/100.0);
                        yRelease = (int) Math.round((mouse.y-50)/100.0);
                        xPosition = xRelease;
                        yPosition = yRelease;

//                        System.out.println("xRelease " + xRelease);
//                        System.out.println("yRelease " + yRelease);

                    }
                    if (e.getSource() == labelWhitePieces[i][j]){
                        Point mouse = boardLabel.getMousePosition();
                        labelWhitePieces[i][j].setLocation(mouse.x - 50, mouse.y - 50);
                        xRelease = (int) Math.round((mouse.x-50)/100.0);
                        yRelease = (int) Math.round((mouse.y-50)/100.0);
                        xPosition = xRelease;
                        yPosition = yRelease;

//                        System.out.println("xRelease " + xRelease);
//                        System.out.println("yRelease " + yRelease);

                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 8; j++) {
                if (clickedBlack && mousePressed) {
                    if (e.getSource() == labelBlackPieces[i][j]){
                        uB = i;
                        vB = j;
                        mousePressed = false;
                        switch (eachPieceName[i][j]) {
                            case "P":
                                pawn();
                                break;
                        }
                    }
                }
                if (clickedWhite && mousePressed) {
                    if (e.getSource() == labelWhitePieces[i][j]){
                        uW = i;
                        vW = j;
                        mousePressed = false;
                        switch (eachPieceName[i][j]) {
                            case "P":
                                pawn();
                                break;
                        }
                    }
                }
            }
        }
    }


    @Override
    public void mouseMoved(MouseEvent e) {



    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
