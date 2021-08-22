package mni.id.game.dice;

public class Dice {
    private int topSideValue;

    public int getTopSideValue() {
        return topSideValue;
    }

    public void setTopSideValue(int topSideValue) {
        this.topSideValue = topSideValue;
    }

    public int roll(){
        return topSideValue = (int)(Math.random()*6) + 1;
    }
}
