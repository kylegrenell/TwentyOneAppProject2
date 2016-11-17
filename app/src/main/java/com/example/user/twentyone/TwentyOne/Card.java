package com.example.user.twentyone.TwentyOne;

/**
 * Created by user on 14/11/2016.
 */

public class Card {

    private Rank mRank;
    private Suit mSuit;


    public Card(Suit s, Rank r) {
        this.mRank = r;
        this.mSuit = s;
    }

    // this should make the face cards 11-12 the value of 10
    // ordinal is order in series, returns the ordinal of this enumeration constant
    public int getCardValue() {
        int value = this.mRank.ordinal() + 1;
        if (value > 10) value = 10;
        return value;
    }

    //  override? - override a method in the superclass... nope.
    public String toString() {
        return this.mRank.name() + " of " + this.mSuit.name();
    }
}


