package com.example.user.twentyone.TwentyOne;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.twentyone.TwentyOne.Player.State.PLAYING;

/**
 * Created by user on 14/11/2016.
 */

public class Player {

    private ArrayList<Card> hand;
    private String name;
    private State playerGameState;

    public Player(String name) {
        name = name;
        hand = new ArrayList<Card>();
        // has a hand with no cards because they're not yet dealt
        playerGameState = State.PLAYING;
        // game state is that they're starting the game (PLAYING)
    }


    private enum State{
        PLAYING,
        WON,
        BUST,
        STAND
    }

    // hits and then returns a card to the hand
    public void hit(Card card){
        hand.add(card);
    }

    //Stand won't ask player to hit
    public void stand(){
        playerGameState = State.STAND;
    }

    // getter for state (ie can do... can't do...)
    public void setState(State state){
        playerGameState = state;
    }


    // print hand to the log
    public void showHand() {
        for (int i = 0; i < hand.size(); i++) {
            Log.d(name, hand.get(i).toString());
        }
    }

    // getter for card count
    public int getCardCount(){
        return hand.size();
    }

    

}
