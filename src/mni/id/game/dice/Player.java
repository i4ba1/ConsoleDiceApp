package mni.id.game.dice;

import java.util.ArrayList;

public class Player {
    private ArrayList<Dice> diceInCup = new ArrayList<>();
    private String name;
    private int position;
    private int point;

    public Player(int numberOfDice, int position, String name){
        this.point = 0;
        this.position = position;
        this.name = name;
        for (int i = 0; i < numberOfDice; i++) {
            diceInCup.add(new Dice());
        }
    }

    public ArrayList<Dice> getDiceInCup() {
        return diceInCup;
    }

    public void removeDice(int dice){
        this.diceInCup.remove(dice);
    }

    public void insertDice(Dice dice) {
        this.diceInCup.add(dice);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPoint() {
        return point;
    }

    public void play(){
        for (Dice dice:diceInCup) {
            dice.roll();
        }
    }

    public void addPoint(int point) {
        this.point = point;
    }
}
