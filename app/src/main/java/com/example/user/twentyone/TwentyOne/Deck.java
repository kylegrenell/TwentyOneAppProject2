package com.example.user.twentyone.TwentyOne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public class Deck {

    private ArrayList<Card> cards;
    // array for suit and rank

    public Deck() {
        cards = new ArrayList<Card>();
        // a deck made up of 4 suits and 13 ranks
        initialiseDeck();
        // initialise the array
    }

    private void initialiseDeck() {

        int count = 0;
        for (int s = 0; s < 4; s++) {
            // s for suit, r for rank
            for (int r = 0; r < 13; r++) {
                cards.add(new Card(s, r));
            }
            count++;
        }
    }

    // shuffle array of cards
    public void shuffle() {
        Collections.shuffle(cards);
    }
}
