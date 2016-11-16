package com.example.user.twentyone.TwentyOne;

/**
 * Created by user on 14/11/2016.
 */

public class Card {

    private Suit suit;
    private Rank rank;

    public Card(int s, int r) {
        suit = Suit.values()[s];
        rank = Rank.values()[r];
    }

//
//    public int getCardValue() {
//        int value = rank.ordinal() + 1;
//        if (value > 10) value = 10;
//        return value;
//    }

    public int getCardValue() {
        if (rank.ordinal() < 9)
            return rank.ordinal();
        else if (rank.ordinal() == 13){
            return 0;
    }
         else
            return 13;
}

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

}
