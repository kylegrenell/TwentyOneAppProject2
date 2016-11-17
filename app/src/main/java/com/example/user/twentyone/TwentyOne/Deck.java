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

    // array for suit and rank
    private ArrayList<Card> cards;


    // deck made up of 4 suits and 13 rank, call initialise method, shuffle the deck
    public Deck() {
        initialiseDeck();
    }


    // initialise the array
    public void initialiseDeck() {

        this.cards = new ArrayList<Card>();

                for ( Rank aRank : Rank.values() ) {
                    for( Suit aSuit : Suit.values() )
                this.cards.add(new Card(aSuit, aRank));
            }
            shuffle();
        }



    // shuffle array of cards
    public void shuffle() {
        Collections.shuffle(this.cards);
    }


    // remove cards deck to decrease deck size
    public void removeCards(){
        for (int i = 0; i < this.cards.size(); i++ )
        {
            Log.v("Deck", this.cards.get(i).toString());
            // log.d is API for sending log output
            // what's the difference with log.v?
        }
    }


    // should deal the cards and then return
    public Card dealCard(){
        return this.cards.remove(0);
    }

}
