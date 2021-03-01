package edu.wctc;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DiceGame {
    private final List<Player> players = new ArrayList<Player>();
    private final List<Die> dice = new ArrayList<Die>();
    private final int maxRolls= 0;
    private Player currentPlayer;

    //functional example. These mostly implement interfaces. i.e. ExpressionCalculator - .compute()
   /**ExpressionCalculator calc = (x, y) -> x / (y * y) * 703;
    double bmi = calc.compute(150.5, 67.0);
    System.out.println(bmi);**/

   /**any functional interface only has one method.
   /** functional expression examples
   () -> expression - no parameters
   parameter -> expression - one parameter
   (p1, p2) -> expression -multiple parameters
   parameter -> { statements; } **/


    //constructor
    public DiceGame(int countPlayers, int countDice, int MaxRolls){
     if (countPlayers < 2){
      throw new IllegalArgumentException();
     }

     else {
      for (int i = 1; i <= countPlayers; i++) {
       Player player = new Player();
       players.add(player);
      }

      for (int i = 1; i <= countDice; i++) {
       Die die = new Die(6);
       dice.add(die);
      }
     }

    }


    private boolean allDiceHeld() {
     return dice.stream().allMatch(die -> die.isBeingHeld());
    }

    //functional - filter, findfirst, ispresent
    //returns a stream consisting of the elements of the stream that match the predicate
    //if already a die with given faceValue held, return true.
    public boolean autoHold(int faceValue) {
    dice.stream().filter(die -> die.getFaceValue()==faceValue); //reduces to stream of only these values
     Optional<Die> result = dice.stream().findFirst();
     if (result.isPresent()){
      if (result.get().isBeingHeld()==false){
       result.get().holdDie();
       return true;
      }
      else {
       return true;
      }
      }
     else {
      return false;
     }
    }


    public boolean currentPlayerCanRoll(){
     if (currentPlayer.getRollsUsed() > 0 && allDiceHeld() == false){
      return true;
     }
     else {
      return false;
     }
    }

    public int getCurrentPlayerNumber() {
     return currentPlayer.getPlayerNumber();
    }

    public int getCurrentPlayerScore() {
      return currentPlayer.getScore();
    }

    // concatenate each die's toString
    public String getDiceResults() {
     //applies function(in this case toString) to all elements
     String diceString = dice.stream().map(dice -> dice.toString()).collect(Collectors.joining());
     return diceString;
    }


    public String getFinalWinner() {
     Collections.sort(players, Comparator.comparingInt(Player::getWins)); //sorts low to high by default
     String result = players.get(players.size() -1).toString(); //returns last value, which is highest
     return result;

    }

    //functional
    //map, Collectors.joining
    public String getGameResults() {

    }

    //this is probably just the same as autoHold but without checking isBeingHeld()?
    private boolean isHoldingDie(int faceValue){
     dice.stream().filter(die -> die.getFaceValue()==faceValue);
     Optional<Die> result = dice.stream().findFirst();
      if (result.isPresent()){
       return true;
      }
      else {
       return false;
      }
    }

    public boolean nextPlayer(){
     if (currentPlayer.getPlayerNumber() < players.size()-1){
      currentPlayer = players.get(currentPlayer.getPlayerNumber() + 1);
      return true;
     }
     else {
      return false;
     }
     }

    //finds the die with the given die number (not face value) and holds it.
    public void playerHold(char dieNum){
     dice.stream().filter(die -> die.getDieNum()==dieNum);
     Optional<Die> result = dice.stream().findFirst();
     result.get().holdDie();
    }


    public void resetDice(){
     dice.forEach(die -> die.resetDie());
    }

    //functional
    public void resetPlayers() {
     players.forEach(player -> player.resetPlayer());

    }

    //functional
    //logs roll for current player, then rolls each die.
    public void rollDice(){

    }

    public void scoreCurrentPlayer(){

    }

    public void startNewGame() {

    }


}
