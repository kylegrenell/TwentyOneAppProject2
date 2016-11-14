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
        name = name;
        hand = new ArrayList<Card>();
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
        WAITING,
        HIT,
        STAND
    }

    //
    public Action askAction(){
        return currentAction;
    }

    // setter
    public void setAction(Action action){
        currentAction = action;
    }

    // hits and then returns a card to the hand
    public void hit(Card card){
        hand.add(card);
    }

    //Stand won't ask player to hit
    public void stand(){
        playerGameState = State.STAND;
    }


    public State getState(){
        return playerGameState;
    }

    // getter for state (ie can do... can't do...)
    public void setState(State state){
        playerGameState = state;
    }


    // print hand to the log
    public void showHand() {
        for (int i = 0; i < hand.size(); i++) {
            Log.v(name, hand.get(i).toString());
        }
    }

    //get the value of the cards in hand
    public int getHandValue(){

        int total = 0;

        for(int i = 0; i < hand.size(); i++)
        total += hand.get(i).getCardValue();

        return total;

    }


    // getter for card count
    public int getCardCount(){
        return hand.size();
    }



}
