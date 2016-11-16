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

    private ArrayList<Card> cards;

    public Deck() {
        initialiseDeck();
    }

    // initialise the array
    public void initialiseDeck() {

        this.cards = new ArrayList<Card>();

        for (int s = 0; s < 4; s++) {
            for (int r = 0; r < 13; r++) {
                this.cards.add(new Card(s, r));
            }
        }
        shuffle();
    }

    // shuffle cards
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    // decrease deck size
    public void removeCards(){
        for (int i = 0; i < this.cards.size(); i++ )
        {
            Log.d("Deck", this.cards.get(i).toString());
        }
    }

    // deal the cards and then return
    public Card dealCard(){
        return this.cards.remove(0);
    }

}