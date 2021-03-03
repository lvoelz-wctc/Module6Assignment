package edu.wctc;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DiceGame {
    private final List<Player> players = new ArrayList<>();
    private final List<Die> dice = new ArrayList<>();
    private final int maxRolls= 5;
    private Player currentPlayer;

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
        return dice.stream().allMatch(Die::isBeingHeld);
    }

    public boolean autoHold(int faceValue) {
    if (isHoldingDie(faceValue)){
        return true;
    }
    else {
        Stream<Die> filteredList = dice.stream().filter(die -> die.getFaceValue()==faceValue); //reduces to stream of only these values
        Optional<Die> result = filteredList.findFirst();
        if (result.isPresent()){
            if (!result.get().isBeingHeld()){
                result.get().holdDie();
                return true;
            }
        }
        return false;
    }
    }

    //how do we send variable in like countDice instead of 5?
    public boolean currentPlayerCanRoll(){
    if (currentPlayer.getRollsUsed() < 5 && !allDiceHeld()){
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

    public String getDiceResults() {
     return dice.stream().map(Die::toString).collect(Collectors.joining());
    }


    public String getFinalWinner() {
     players.sort(Comparator.comparingInt(Player::getWins));
     return players.get(players.size() -1).toString();
    }

    //Comparator.comparingInt, reversed, forEach, map, Collectors.joining
    public String getGameResults() {
        Stream<Player> result = players.stream().
                sorted(Comparator.comparingInt(Player::getScore).reversed());

        List<Player> sortedPlayerList = result.collect(Collectors.toList());
        int count = 0;

        int winValue = sortedPlayerList.get(0).getScore();
        if (winValue != 0){
            for (Player player : sortedPlayerList) {
                if (player.getScore() == winValue) {
                    player.addWin();
                    count = count+1;
                }
                else {player.addLoss();}
            }
        }
        return sortedPlayerList.stream().map(Player::toString).collect(Collectors.joining());
    }

    private boolean isHoldingDie(int faceValue){
     Stream<Die> filteredList = dice.stream().filter(die -> die.getFaceValue()==faceValue).filter(Die::isBeingHeld);
     Optional<Die> result = filteredList.findFirst();
      if (result.isPresent()){
       return true;
      }
      else {
       return false;
      }
    }


    public boolean nextPlayer(){
     if (currentPlayer.getPlayerNumber() < players.size()){
      currentPlayer = players.get(currentPlayer.getPlayerNumber());
      return true;
     }
     else {
      return false;
     }
     }

    //filter, findfirst, ispresent
    public void playerHold(char dieNum){
     Stream<Die> holdStream = dice.stream().filter(die -> die.getDieNum()==dieNum);
     Optional<Die> result = holdStream.findFirst();
     result.get().holdDie();
    }


    public void resetDice(){
     dice.forEach(Die::resetDie);
    }

    public void resetPlayers() {
     players.forEach(Player::resetPlayer);
    }


    public void rollDice(){
      currentPlayer.roll();
      dice.forEach(Die::rollDie);
    }

    public void scoreCurrentPlayer(){
        //this first line confirms that all dice are held, so that it doesn't just jump to it and return nothing.
        //if (allDiceHeld() && isHoldingDie(6) && isHoldingDie(5) && isHoldingDie(4)){
            List<Integer> poppedDie = new ArrayList<>();
            List<Integer> scoringDie = new ArrayList<>();
            for (Die d : dice) {
                if (d.getFaceValue() == 6 && !poppedDie.contains(6) || d.getFaceValue() == 5 && !poppedDie.contains(5) || d.getFaceValue() == 4 && !poppedDie.contains(4)){
                    poppedDie.add(d.getFaceValue());
                }
                else {
                    scoringDie.add(d.getFaceValue());
                }
            }

            int totalScore = 0;

            //only fires if poppedDie has ship, captain, and crew
            if (poppedDie.contains(6) & poppedDie.contains(5) && poppedDie.contains(4)){
                for (Integer i : scoringDie){
                    totalScore = totalScore + i;
                }
            }
            currentPlayer.setScore(getCurrentPlayerScore()+totalScore);
        //}
    }


    public void startNewGame() {
      currentPlayer = players.get(0);
      resetPlayers();
    }
}
