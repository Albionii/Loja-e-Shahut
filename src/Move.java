import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Move implements MouseListener, MouseMotionListener {

    private final JLabel[][] labelWhitePieces;
    private final JLabel[][] labelBlackPieces;
    final private JLabel boardLabel;
    private boolean clickedBlack, clickedWhite, mousePressed;
    private int xPosition, yPosition;
    private int uB, vB, uW, vW;
    private final String[][] eachPieceName = new String[2][8];
    private final SavePosition[][] savePositionForWhite;
    private final SavePosition[][] savePositionForBlack;
    private char bOrw;
    int xRelease, yRelease;

    public boolean wasPieceTaken = false;

    public Move(JLabel[][] labelWhitePieces, JLabel[][] labelBlackPieces, JLabel boardLabel, SavePosition[][] savePositionForWhite, SavePosition[][] savePositionForBlack) {
        this.labelWhitePieces = labelWhitePieces;
        this.labelBlackPieces = labelBlackPieces;
        this.boardLabel = boardLabel;
        this.savePositionForWhite = savePositionForWhite;
        this.savePositionForBlack = savePositionForBlack;
        addListenerToLabels();
        setPieceName();
    }


    /**
     * Per te bere te gjitha labels funksionale me ane te mausit, kam krijuar kete metode
     * qe therret metodat addMouseListener() and addMouseMotionListener()
     */
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


    /**
     * Krijon nje varg i cili permban inicialet e figurave te shahut ne menyre qe t'i qasem
     * kur kam nevoje.
     */
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


    /**
     * Nese lojtari luan nje levizje jo legale te figures, kjo metode e therret ate ne poziten paraprake.
     */
    public void restartPreviousMove() {
        if (clickedBlack){
            labelBlackPieces[uB][vB].setBounds(savePositionForBlack[uB][vB].getXPosition()*100, savePositionForBlack[uB][vB].getYPosition()*100, 100, 100);
            clickedBlack = false;
            clickedWhite = true;
        }else if (clickedWhite) {
            labelWhitePieces[uW][vW].setBounds(savePositionForWhite[uW][vW].getXPosition()*100, (savePositionForWhite[uW][vW].getYPosition())*100, 100, 100);
            clickedWhite = false;
            clickedBlack = true;
        }
    }


    /**
     * Kjo metode shikon nese kane mbetur me piuna ne rreshtin e tyre fillestar. Kjo sepse nje piun ne hapin e pare
     * mund t'i bej dy levizje ose vetem nje, ndersa pas asaj levizjeje cdohere leviz vetem nje here.
     * @return true nese piuni eshte ne rreshtin fillestar.
     * false nese piuni nuk eshte ne ate rresht.
     *
     */
    public boolean isPawnInFirstRank() {
        if (clickedBlack) {
            if (savePositionForBlack[uB][vB].getYPosition() == 1) {
                return true;
            }
        }
        else if (clickedWhite) {
            if (savePositionForWhite[uW][vW].getYPosition() == 6) {
                return true;
            }
        }
        return false;
    }


    /**
     * Kjo eshte metoda themelore per levizjen e piunit. Kjo metode akoma eshte ne perpunim por ajo teston nese piuni
     * mund te bej levizje, nese eshte legale dhe nese mund te marre ndonje figure.
     */
    public void pawn() {


        // Ketu eshte kodi kur nje piun diagonalisht e merr nje piun te kundershtarit.


        if (clickedBlack && canThePieceTake(savePositionForBlack[uB][vB], pieceInThatPosition(savePositionForBlack[uB][vB]))) {
            updatePiecePozition(savePositionForBlack[uB][vB]);
            labelWhitePieces[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setBounds(900, 100, 100, 100);
            savePositionForWhite[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setXPosition(9);
        }
        else if (clickedWhite && canThePieceTake(savePositionForWhite[uW][vW], pieceInThatPosition(savePositionForWhite[uW][vW]))) {
            updatePiecePozition(savePositionForWhite[uW][vW]);
            labelBlackPieces[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setBounds(900, 100, 100, 100);
            savePositionForBlack[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setXPosition(9);
        }
        else {

            /*
            * Shikon se pari cfare ngjyre eshte figura.
            * Shikon nese figura ka levizje legale.
            * Ne qofte se jo ktheje ne levizjen paraprake.
            * Ne te kunderten levize aty.
            * */

            if (clickedBlack) {
                if (xPosition == savePositionForBlack[uB][vB].getXPosition() && yPosition == savePositionForBlack[uB][vB].getYPosition()+1) {
                    if(isThereAPiece(savePositionForBlack[uB][vB])){
                        restartPreviousMove();
                    }
                    else{
                        updatePiecePozition(savePositionForBlack[uB][vB]);
                    }
                }
                else if (xPosition == savePositionForBlack[uB][vB].getXPosition() && yPosition == (savePositionForBlack[uB][vB].getYPosition()+2) && isPawnInFirstRank()){
                    if(isThereAPiece(savePositionForBlack[uB][vB])){
                        restartPreviousMove();
                    }
                    else{
                        updatePiecePozition(savePositionForBlack[uB][vB]);
                    }
                }
                else {
                    restartPreviousMove();
                }
            }
            else if (clickedWhite) {
                if (xPosition == savePositionForWhite[uW][vW].getXPosition() && yPosition == ((savePositionForWhite[uW][vW].getYPosition())-1)) {
                    if (isThereAPiece(savePositionForWhite[uW][vW])){
                        restartPreviousMove();
                    }
                    else{
                        updatePiecePozition(savePositionForWhite[uW][vW]);
                    }
                }
                else if (xPosition == savePositionForWhite[uW][vW].getXPosition() && yPosition == ((savePositionForWhite[uW][vW].getYPosition())-2) && isPawnInFirstRank()) {
                    if (isThereAPiece(savePositionForWhite[uW][vW])){
                        restartPreviousMove();
                    }
                    else{
                        updatePiecePozition(savePositionForWhite[uW][vW]);
                    }
                }
                else {
                    restartPreviousMove();
                }
            }
        }
    }

    public void rook() {

        if (clickedBlack && canThePieceTake(savePositionForBlack[uB][vB], pieceInThatPosition(savePositionForBlack[uB][vB]))) {
            updatePiecePozition(savePositionForBlack[uB][vB]);
            labelWhitePieces[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setBounds(900, 100, 100, 100);
            savePositionForWhite[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setXPosition(9);
            System.out.println("Black takes");
        }
        else if (clickedWhite && canThePieceTake(savePositionForWhite[uW][vW], pieceInThatPosition(savePositionForWhite[uW][vW]))) {
            updatePiecePozition(savePositionForWhite[uW][vW]);
            labelBlackPieces[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setBounds(900, 100, 100, 100);
            savePositionForBlack[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setXPosition(9);
            System.out.println("White takes");
        }
        else {
            if (clickedBlack) {
                if (xPosition == savePositionForBlack[uB][vB].getXPosition()){
                    if (isThereAPiece(savePositionForBlack[uB][vB])){
                        restartPreviousMove();
                    }
                    else {
                        updatePiecePozition(savePositionForBlack[uB][vB]);
                    }
                }
                else if (yPosition == savePositionForBlack[uB][vB].getYPosition()){
                    if (isThereAPiece(savePositionForBlack[uB][vB])){
                        restartPreviousMove();
                    }
                    else {
                        updatePiecePozition(savePositionForBlack[uB][vB]);
                    }
                }
                else {
                    restartPreviousMove();
                }

            }
            else if (clickedWhite) {
                if (xPosition == savePositionForWhite[uW][vW].getXPosition()) {
                    if (isThereAPiece(savePositionForWhite[uW][vW])) {
                        restartPreviousMove();
                    } else {
                        updatePiecePozition(savePositionForWhite[uW][vW]);
                    }
                } else if (yPosition == savePositionForWhite[uW][vW].getYPosition()) {
                    if (isThereAPiece(savePositionForWhite[uW][vW])) {
                        restartPreviousMove();
                    } else {
                        updatePiecePozition(savePositionForWhite[uW][vW]);
                    }
                } else {
                    restartPreviousMove();
                }
            }
        }
    }

    public void bishop() {
        if (clickedBlack && canThePieceTake(savePositionForBlack[uB][vB], pieceInThatPosition(savePositionForBlack[uB][vB]))) {
            updatePiecePozition(savePositionForBlack[uB][vB]);
            labelWhitePieces[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setBounds(900, 100, 100, 100);
            savePositionForWhite[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setXPosition(9);
            System.out.println("Black takes");
        }
        else if (clickedWhite && canThePieceTake(savePositionForWhite[uW][vW], pieceInThatPosition(savePositionForWhite[uW][vW]))) {
            updatePiecePozition(savePositionForWhite[uW][vW]);
            labelBlackPieces[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setBounds(900, 100, 100, 100);
            savePositionForBlack[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setXPosition(9);
            System.out.println("White takes");
        }
        else {

            if (clickedBlack) {
                int k = xPosition - savePositionForBlack[uB][vB].getXPosition();
                if (yPosition == savePositionForBlack[uB][vB].getYPosition() + k || yPosition == savePositionForBlack[uB][vB].getYPosition() - k){
                    if (isThereAPiece(savePositionForBlack[uB][vB])){
                        restartPreviousMove();
                    }
                    else {
                        updatePiecePozition(savePositionForBlack[uB][vB]);
                    }
                }
                else {
                    restartPreviousMove();
                }
            }else if (clickedWhite) {
                int k = xPosition - savePositionForWhite[uW][vW].getXPosition();
                if (yPosition == savePositionForWhite[uW][vW].getYPosition() + k || yPosition == savePositionForWhite[uW][vW].getYPosition() - k){
                    if (isThereAPiece(savePositionForWhite[uW][vW])){
                        restartPreviousMove();
                    }
                    else {
                        updatePiecePozition(savePositionForWhite[uW][vW]);
                    }
                }
                else {
                    restartPreviousMove();
                }
            }
        }
    }

    public void knight() {
        if (clickedBlack && canThePieceTake(savePositionForBlack[uB][vB], pieceInThatPosition(savePositionForBlack[uB][vB]))) {
            updatePiecePozition(savePositionForBlack[uB][vB]);
            labelWhitePieces[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setBounds(900, 100, 100, 100);
            savePositionForWhite[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setXPosition(9);
            System.out.println("Black takes");
        } else if (clickedWhite && canThePieceTake(savePositionForWhite[uW][vW], pieceInThatPosition(savePositionForWhite[uW][vW]))) {
            updatePiecePozition(savePositionForWhite[uW][vW]);
            labelBlackPieces[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setBounds(900, 100, 100, 100);
            savePositionForBlack[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setXPosition(9);
            System.out.println("White takes");
        }
        else {


            if (clickedBlack) {
                int kX = Math.abs(xPosition - savePositionForBlack[uB][vB].getXPosition());
                int kY = Math.abs(yPosition - savePositionForBlack[uB][vB].getYPosition());
                if (kX + kY == 3 && kX != 0 && kY != 0) {
                    if (isThereAPiece(savePositionForBlack[uB][vB])) {
                        restartPreviousMove();
                    } else {
                        updatePiecePozition(savePositionForBlack[uB][vB]);
                    }
                } else {
                    restartPreviousMove();
                }
            } else if (clickedWhite) {
                int kX = Math.abs(xPosition - savePositionForWhite[uW][vW].getXPosition());
                int kY = Math.abs(yPosition - savePositionForWhite[uW][vW].getYPosition());
                if (kX + kY == 3 && kX != 0 && kY != 0) {
                    if (isThereAPiece(savePositionForWhite[uW][vW])) {
                        restartPreviousMove();
                    } else {
                        updatePiecePozition(savePositionForWhite[uW][vW]);
                    }
                } else {
                    restartPreviousMove();
                }
            }
        }
    }

    public void queen() {
        if (clickedBlack && canThePieceTake(savePositionForBlack[uB][vB], pieceInThatPosition(savePositionForBlack[uB][vB]))) {
            updatePiecePozition(savePositionForBlack[uB][vB]);
            labelWhitePieces[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setBounds(900, 100, 100, 100);
            savePositionForWhite[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setXPosition(9);
            System.out.println("Black takes");
        } else if (clickedWhite && canThePieceTake(savePositionForWhite[uW][vW], pieceInThatPosition(savePositionForWhite[uW][vW]))) {
            updatePiecePozition(savePositionForWhite[uW][vW]);
            labelBlackPieces[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setBounds(900, 100, 100, 100);
            savePositionForBlack[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setXPosition(9);
            System.out.println("White takes");
        }
        else {
            if (clickedBlack) {
                int k = xPosition - savePositionForBlack[uB][vB].getXPosition();
                if (xPosition == savePositionForBlack[uB][vB].getXPosition() || yPosition == savePositionForBlack[uB][vB].getYPosition()){
                    if (isThereAPiece(savePositionForBlack[uB][vB])){
                        restartPreviousMove();
                    }
                    else {
                        updatePiecePozition(savePositionForBlack[uB][vB]);
                    }
                }
                else if (yPosition == savePositionForBlack[uB][vB].getYPosition() + k || yPosition == savePositionForBlack[uB][vB].getYPosition() - k){
                    if (isThereAPiece(savePositionForBlack[uB][vB])){
                        restartPreviousMove();
                    }
                    else {
                        updatePiecePozition(savePositionForBlack[uB][vB]);
                    }
                }
                else {
                    restartPreviousMove();
                }
            }
            else if (clickedWhite) {
                int k = xPosition - savePositionForWhite[uW][vW].getXPosition();
                if (xPosition == savePositionForWhite[uW][vW].getXPosition() || yPosition == savePositionForWhite[uW][vW].getYPosition()){
                    if (isThereAPiece(savePositionForWhite[uW][vW])){
                        restartPreviousMove();
                    }
                    else {
                        updatePiecePozition(savePositionForWhite[uW][vW]);
                    }
                }
                else if (yPosition == savePositionForWhite[uW][vW].getYPosition() + k || yPosition == savePositionForWhite[uW][vW].getYPosition() - k){
                    if (isThereAPiece(savePositionForWhite[uW][vW])){
                        restartPreviousMove();
                    }
                    else {
                        updatePiecePozition(savePositionForWhite[uW][vW]);
                    }
                }
                else {
                    restartPreviousMove();
                }
            }
        }
    }

    public void king() {
        if (clickedBlack && canThePieceTake(savePositionForBlack[uB][vB], pieceInThatPosition(savePositionForBlack[uB][vB]))) {
            updatePiecePozition(savePositionForBlack[uB][vB]);
            labelWhitePieces[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setBounds(900, 100, 100, 100);
            savePositionForWhite[pieceInThatPosition(savePositionForBlack[uB][vB]).labelY][pieceInThatPosition(savePositionForBlack[uB][vB]).labelX].setXPosition(9);
            System.out.println("Black takes");
        } else if (clickedWhite && canThePieceTake(savePositionForWhite[uW][vW], pieceInThatPosition(savePositionForWhite[uW][vW]))) {
            updatePiecePozition(savePositionForWhite[uW][vW]);
            labelBlackPieces[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setBounds(900, 100, 100, 100);
            savePositionForBlack[pieceInThatPosition(savePositionForWhite[uW][vW]).labelY][pieceInThatPosition(savePositionForWhite[uW][vW]).labelX].setXPosition(9);
            System.out.println("White takes");
        }
        else {
            if (clickedBlack){
                int kingi1 = Math.abs(xPosition - savePositionForBlack[uB][vB].getXPosition());
                int kingi2 = Math.abs(yPosition - savePositionForBlack[uB][vB].getYPosition());
                if (kingi1 * kingi2 == 1 || kingi1 * kingi2 == 0) {
                    if (isThereAPiece(savePositionForBlack[uB][vB])){
                        restartPreviousMove();
                    }
                    else {
                        updatePiecePozition(savePositionForBlack[uB][vB]);
                    }
                }
                else {
                    restartPreviousMove();
                }
            }
            else if (clickedWhite) {
                int kingi1 = Math.abs(xPosition - savePositionForWhite[uW][vW].getXPosition());
                int kingi2 = Math.abs(yPosition - savePositionForWhite[uW][vW].getYPosition());
                if (kingi1 * kingi2 == 1 || kingi1 * kingi2 == 0) {
                    if (isThereAPiece(savePositionForWhite[uB][vW])){
                        restartPreviousMove();
                    }
                    else {
                        updatePiecePozition(savePositionForWhite[uW][vW]);
                    }
                }
                else {
                    restartPreviousMove();
                }
            }

        }

    }


    /**
     * Ben perditesimin e pozites se figures. Kjo metode thirret nga metoda pawn().
     * @param s Si paramater ka klasen SavesPosition qe ruan vlerat per poziten e figures, ngjyren etj.
     */
    public void updatePiecePozition(SavePosition s) {
        clickedBlack = s.whiteOrBlack == 'b';
        clickedWhite = s.whiteOrBlack != 'b';
        if (clickedBlack) {
            labelBlackPieces[s.labelY][s.labelX].setBounds(xPosition*100, yPosition*100, 100, 100);
        }
        else {
            labelWhitePieces[s.labelY][s.labelX].setBounds(xPosition*100, yPosition*100, 100, 100);
        }
        s.setXPosition(xPosition);
        s.setYPosition(yPosition);
    }


    /**
     * Shikon nese figura mund te marre figuren tjeter ne qofte se e ka rradhen. Kerkohen dy figura si parametra.
     * @param s1 Figura e pare.
     * @param s2 Figura e dyte.
     * @return true nese figura mund te mirret dhe false ne te kunderten.
     */
    public boolean canThePieceTake(SavePosition s1, SavePosition s2) {
        boolean doNotMoveOnClick = s1.getXPosition() == xPosition && s1.getYPosition() == yPosition;
        if (doNotMoveOnClick){
            switch (eachPieceName[s1.labelY][s1.labelX]){
                case "P":
                    if(s1.whiteOrBlack == 'w' && s2.whiteOrBlack == 'b') {
                        boolean arePawnsInRightXPos = s2.getXPosition() - s1.getXPosition() == 1 || s2.getXPosition() - s1.getXPosition() == -1;
                        boolean arePawnsInRightYPos = s2.getYPosition() - s1.getYPosition() == -1;
                        return arePawnsInRightXPos && arePawnsInRightYPos;
                    }
                    else if (s1.whiteOrBlack == 'b' && s2.whiteOrBlack == 'w'){
                        boolean arePawnsInRightXPos = s2.getXPosition() - s1.getXPosition() == 1 || s2.getXPosition() - s1.getXPosition() == -1;
                        boolean arePawnsInRightYPos = s2.getYPosition() - s1.getYPosition() == 1;
                        return arePawnsInRightXPos && arePawnsInRightYPos;
                    }
                    else {
                        return false;
                    }
                case "R":
                    if (s1.whiteOrBlack != s2.whiteOrBlack) {
                        boolean rookXAligned = s1.getXPosition() == s2.getXPosition();
                        boolean rookYAligned = s1.getYPosition() == s2.getYPosition();
                        return rookYAligned || rookXAligned;
                    }
                case "B":
                    if (s1.whiteOrBlack != s2.whiteOrBlack) {
                        int k = s1.getXPosition() - s2.getXPosition();
                        return s2.getYPosition() + k == s1.getYPosition()|| s2.getYPosition() - k == s1.getYPosition();
                    }
                case "N":
                    if (s1.whiteOrBlack != s2.whiteOrBlack) {
                        int kX = Math.abs(s1.getXPosition() - s2.getXPosition());
                        int kY = Math.abs(s1.getYPosition() - s2.getYPosition());
                        return kX + kY == 3 && kX != 0 && kY != 0;
                    }
                case "Q":
                    if (s1.whiteOrBlack != s2.whiteOrBlack) {
                        boolean rookXAligned = s1.getXPosition() == s2.getXPosition();
                        boolean rookYAligned = s1.getYPosition() == s2.getYPosition();
                        int k = s1.getXPosition() - s2.getXPosition();
                        return s2.getYPosition() + k == s1.getYPosition() || s2.getYPosition() - k == s1.getYPosition() || (rookYAligned || rookXAligned);
                    }
                case "K":
                    if (s1.whiteOrBlack != s2.whiteOrBlack) {
                        boolean k1 = s1.getYPosition() == s2.getYPosition() && (s1.getXPosition() == s2.getXPosition() + 1 || s1.getXPosition() == s2.getXPosition() - 1);
                        boolean k2 = s1.getXPosition() == s2.getXPosition() && (s1.getYPosition() == s2.getYPosition() + 1 || s1.getYPosition() == s2.getYPosition() - 1);
                        return k1 || k2;
                    }
            }
        }

        return false;
    }

    /**
     * Ne ate figure qe duam ta marrim, gjen se cila eshte ajo.
     * @param s Figura me te cilen e bejme drag and drop.
     * @return Figura te cilen e kemi bere drop dhe duam ta marrim.
     */
    public SavePosition pieceInThatPosition (SavePosition s) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (savePositionForBlack[i][j].getXPosition() == xPosition && savePositionForBlack[i][j].getYPosition() == yPosition && savePositionForBlack[i][j] != s){
                    return savePositionForBlack[i][j];
                }
                else if (savePositionForWhite[i][j].getXPosition() == xPosition && savePositionForWhite[i][j].getYPosition() == yPosition && savePositionForWhite[i][j] != s){
                    return savePositionForWhite[i][j];
                }
            }
        }
        return s;
    }


    /**
     * Shikon nese ne ate pozite jane dy figura, pra nese eshte lejuar marrja e figures. Metoda e perjashton figuren qe e leviz perdoruesi
     * andaj ne te vertete teston vetem nese ne poziten qe duam ekziston ndonje figure.
     * @param savePosition Figura qe e testojme ose e bejme drag and drop.
     * @return true nese jane dy figura mbi njera-tjetren dhe false ne te kunderten.
     */
    public boolean isThereAPiece(SavePosition savePosition) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (savePositionForBlack[i][j].getXPosition() == xPosition && savePositionForBlack[i][j].getYPosition() == yPosition && savePositionForBlack[i][j] != savePosition){
                    return true;
                }
                else if (savePositionForWhite[i][j].getXPosition() == xPosition && savePositionForWhite[i][j].getYPosition() == yPosition && savePositionForWhite[i][j] != savePosition){
                    return true;
                }
            }
        }
        return false;
    }





    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Vepron se cfare duhet ndodh me shtypjen e tastit ne ndonje figure te shahut.
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 8; j++) {
                if (e.getSource() == labelBlackPieces[i][j] && !clickedBlack){
                    mousePressed = true;
                    clickedBlack = true;
                    clickedWhite = false;
                    uB = i;
                    vB = j;
                }
                if (e.getSource() == labelWhitePieces[i][j] && !clickedWhite){
                    mousePressed = true;
                    clickedWhite = true;
                    clickedBlack = false;
                    uW = i;
                    vW = j;
                }
            }
        }
    }

    /**
     * Me ane te kesaj metode mundesohet drag i te gjitha figurave.
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (mousePressed) {
            if (e.getSource() == labelBlackPieces[uB][vB]){
                Point mouse = boardLabel.getMousePosition();
                labelBlackPieces[uB][vB].setLocation(mouse.x - 50, mouse.y - 50);
                //xRelease eshte vetem vlere prej 0 deri ne 7 sipas formules se meposhtme kjo mundeson qe ta dime se ne cilen kuti ndodhet figura gjate levizjes.
                xRelease = (int) Math.round((mouse.x-50)/100.0);
                yRelease = (int) Math.round((mouse.y-50)/100.0);
                xPosition = xRelease;
                yPosition = yRelease;
            }
            if (e.getSource() == labelWhitePieces[uW][vW]){
                Point mouse = boardLabel.getMousePosition();
                labelWhitePieces[uW][vW].setLocation(mouse.x - 50, mouse.y - 50);
                xRelease = (int) Math.round((mouse.x-50)/100.0);
                yRelease = (int) Math.round((mouse.y-50)/100.0);
                xPosition = xRelease;
                yPosition = yRelease;
            }
        }
    }

    /**
     * Kjo eshte metoda qe e mundeson drop qe behet me leshimin e tastit te majte.
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (clickedBlack && mousePressed) {
            if (e.getSource() == labelBlackPieces[uB][vB]){
                mousePressed = false;
                switch (eachPieceName[uB][vB]) {
                    case "P":
                        pawn();
                        break;
                    case "R":
                        rook();
                        break;
                    case "B":
                        bishop();
                        break;
                    case "N" :
                        knight();
                        break;
                    case "Q":
                        queen();
                        break;
                    case "K":
                        king();
                        break;
                }
            }
        }
        if (clickedWhite && mousePressed) {
            if (e.getSource() == labelWhitePieces[uW][vW]){
                mousePressed = false;
                switch (eachPieceName[uW][vW]) {
                    case "P":
                        pawn();
                        break;
                    case "R":
                        rook();
                        break;
                    case "B":
                        bishop();
                        break;
                    case "N" :
                        knight();
                        break;
                    case "Q":
                        queen();
                        break;
                    case "K":
                        king();
                        break;
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
