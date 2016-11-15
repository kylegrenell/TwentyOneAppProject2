package com.example.user.twentyone.TwentyOne;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public class Deck {

    // array for suit and rank *******************************************
    private ArrayList<Card> cards;


    // a deck made up of 4 suits and 13 ranks ****************************
    // call initialise method
    // shuffle the deck
    public Deck() {
        cards = new ArrayList<Card>();
        initialiseDeck();
        shuffle();
    }


    // initialise the array***********************************************
    private void initialiseDeck() {

        int count = 0;
        for (int s = 0; s < 4; s++) {
            // i for suit, r for rank, ++ increments
            for (int r = 0; r < 13; r++) {
                cards.add(new Card(s, r));
            }
            count++;
        }
    }


    // shuffle array of cards**********************************************
    public void shuffle() {
        Collections.shuffle(cards);
    }



    // remove cards deck to decrease deck size*****************************
    public void removeCards(){
        for (int i = 0; i < cards.size(); i++ )
        {
            Log.v("Deck", cards.get(i).toString());
            // log.d is API for sending log output
            // what's the difference with log.v?
        }
    }


    // should deal the cards and then return *******************************
    public Card dealCard(){
        return cards.remove(0);
    }

}
