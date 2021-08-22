package mni.id.game.dice;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int numberOfPlayer;
    private int numberOfDicePerPlayer;
    private int round;
    private List<Player> players;

    private static final int REMOVED_WHEN_DICE_TOP = 6;
    private static final int MOVE_WHEN_DICE_TOP = 1;

    public static void main(String[] args){
        Game game = new Game(3, 4);
        game.start();
    }

    public Game(int numberOfPlayer, int numberOfDicePerPlayer){
        this.round = 0;
        this.numberOfPlayer = numberOfPlayer;
        this.numberOfDicePerPlayer = numberOfDicePerPlayer;
        players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayer; i++) {
            players.add(new Player(numberOfDicePerPlayer, i, "#"+(i+1)));
        }
    }

    private String displayRound(){
        return "Giliran "+this.round+" lempar dadu";
    }

    private String displayToSideDice(String title){
        System.out.println(title);
        String diceTopSide = null;
        int index = 0;
        for (Player p:players) {
            String diceNumber = "";
            if (p.getDiceInCup() == null || p.getDiceInCup().size() <= 0) {
                return "_(Berhenti bermain karena tidak memiliki dadu)";
            }else {
                for (Dice dice : p.getDiceInCup()) {
                    diceNumber += dice.getTopSideValue() + ",";
                }

                diceNumber = diceNumber.substring(0, diceNumber.length() - 1);
                System.out.println("Pemain " + p.getName() + "(" + index + "):" + diceNumber);
                diceTopSide = "";
                for (Dice d : p.getDiceInCup()) {
                    diceTopSide += d.getTopSideValue() + ",";
                }

                //remove last comma
                diceTopSide = diceTopSide.substring(0, diceTopSide.length() - 1);
            }
        }
        return diceTopSide;
    }

    private String displayWinner(Player p){
        System.out.println("Pemenang");
        return "Pemain "+p.getName();
    }

    public void start(){
        System.out.println("Pemain="+numberOfPlayer+ " Dadu="+numberOfDicePerPlayer);
        int turn = 0;
        while (true){
            List<List<Dice>> diceCarryForward = new ArrayList<>();
            for (Player player:players) {
                player.play();
            }

            int point = 0;
            displayToSideDice("Lempar Dadu");
            for (Player player:players) {
                List<Dice> tempDice = new ArrayList<>();
                round++;
                System.out.println(displayRound());

                //Evaluation
                for (int diceIndex=0; diceIndex<player.getDiceInCup().size(); diceIndex++) {
                    Dice d = player.getDiceInCup().get(diceIndex);
                    //If the dice number is 6
                    if (d.getTopSideValue() == REMOVED_WHEN_DICE_TOP) {
                        point++;
                        player.addPoint(point);
                        player.removeDice(diceIndex);
                    }else if (d.getTopSideValue() == MOVE_WHEN_DICE_TOP) {
                        player.insertDice(d);
                        player.removeDice(diceIndex);
                        if (player.getPosition()+1 < numberOfPlayer) {
                            players.get(player.getPosition()+1).insertDice(d);
                        }
                    }
                }
            }

            displayToSideDice("Setelah Evaluasi");
            int playerHasDice = numberOfPlayer;
            for (Player player:players) {
                if (player.getDiceInCup().size() <= 0) {
                    playerHasDice--;
                }
            }

            if (playerHasDice == 1) {
                displayWinner(getWinner());
                break;
            }
        }
    }

    private Player getWinner(){
        Player winner = null;
        int highScore = 0;
        for (Player p:players) {
            if (p.getPoint() > highScore) {
                highScore = p.getPoint();
                winner = p;
            }
        }
        return winner;
    }
}
