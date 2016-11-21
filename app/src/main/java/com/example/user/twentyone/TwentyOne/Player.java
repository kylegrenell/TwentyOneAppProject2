package com.example.user.twentyone.TwentyOne;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 14/11/2016.
 */

public class Player {

    private ArrayList<Card> hand;
    private String name;
    private State playerGameState;
    private Action currentAction;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<Card>();
        this.playerGameState = State.PLAYING;
    }

    public enum State{
        PLAYING,
        WON,
        LOST,
        PUSH,
        BUST,
        STAND
    }

    public enum Action{
        HIT,
        STAND,
        WAIT
    }

    // getters
    public String getName(){
        return this.name;
    }


    // what action does the player want to do ie wait, hit or stand
    public Action askAction(){
        return this.currentAction;
    }


    // setter for the action enum
    public void setAction(Action action){
        this.currentAction = action;
    }


    // hits and then returns a card to the hand
    public void hit(Card card){
        this.hand.add(card);
    }


    //Stand won't ask player to hit (end their turn)
    public void stand(){
        this.playerGameState = State.STAND;
    }

    // getter for state of the player in the game
    public State getState(){
        return this.playerGameState;
    }


    // setter for state (ie can do... can't do...)
    public void setState(State state){
        this.playerGameState = state;
    }


    // clear the players hand
    public void clearHand(){
        this.hand.clear();
    }


    // print hand to the log
    public void showHand() {
        for (int i = 0; i < this.hand.size(); i++) {
            Log.v(this.name, this.hand.get(i).toString());
        }
    }

    //get the value of the cards in hand
    public int getHandValue() {

        int total = 0;
//        int aceCount = 0;
//        if total > 21 ace = 1 else ace = 10

        for (int i = 0; i < this.hand.size(); i++)
            if (this.hand.get(i).getCardValue() != 13)
                total += this.hand.get(i).getCardValue();
        return total;
    }
//            else
//                aceCount++;

//        for(int i = 0; i < aceCount; i++)
//        {
//            total += 10; // first ace equal to 10 counted as a high
//            if(total > 21) // without the ace
//                total -= 9; // takes away 9 from the ace
//        }
//        return total;
//
//    }

    // getter for card count
    public int getCardCount(){
        return this.hand.size();
    }

    public String printHandValue () {
        String result = " ";
        for ( int i = 0 ; i < this.hand.size() ; i++ ) {
            result += hand.get(i).getCardName();
        }
        return result;
    }

}
