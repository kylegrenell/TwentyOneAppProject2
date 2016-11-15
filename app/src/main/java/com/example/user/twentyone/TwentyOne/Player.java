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
        // has a hand with no cards because they're not yet dealt
        playerGameState = State.PLAYING;
        // game state is that they're starting the game (PLAYING)
    }

    public enum State{
        PLAYING,
        WON,
        BUST,
        STAND
    }

    // get current actions
    public enum Action{
        HIT,
        STAND
    }

    // what action does the player want to do ie wait, hit or stand
    public Action askAction(){
        return currentAction;
    }

    // setter for the action enum
    public void setAction(Action action){
        currentAction = action;
    }

    // hits and then returns a card to the hand
    public void hit(Card card){
        hand.add(card);
    }

    //Stand won't ask player to hit (end their turn)
    public void stand(){
        playerGameState = State.STAND;
    }

    // getter for state of the player in the game
    public State getState(){
        return playerGameState;
    }

    // setter for state (ie can do... can't do...)
    public void setState(State state){
        playerGameState = state;
    }

    // print hand to the log
    public void showHand() {
        for (int i = 0; i < hand.size(); i++) {
            Log.v(this.name, hand.get(i).toString());
        }
    }

    //get the value of the cards in hand
    public int getHandValue(){

        int total = 0;

//        total value of all other cards
        for(int i = 0; i < hand.size(); i++)
        total += hand.get(i).getCardValue();
//        if total > 21 ace = 1 else ace = 10
        // whatever aces are left add on
        return total;

    }

    // getter for card count
    public int getCardCount(){
        return hand.size();
    }

}
