public class SavePosition {
    public int labelX, labelY;
    public int xPosition, yPosition;
    public char whiteOrBlack;

    public SavePosition(int labelX, int labelY, int xPosition, int yPosition, char whiteOrBlack) {
        this.labelX = labelX;
        this.labelY = labelY;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.whiteOrBlack = whiteOrBlack;
    }

    public int getXPosition(){
        return xPosition;
    }
    public int getYPosition(){
        return yPosition;
    }

    public void setXPosition(int x) {
        xPosition = x;
    }
    public void setYPosition(int y) {
        yPosition = y;
    }

}
