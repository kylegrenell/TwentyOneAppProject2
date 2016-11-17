package com.example.user.twentyone.TwentyOne;

/**
 * Created by user on 14/11/2016.
 */

public class Card {

    private Rank mRank;
    private Suit mSuit;

    public Card(int s, int r) {
        this.mRank = mRank.values()[r];
        this.mSuit = mSuit.values()[s];
    }

    // this should make the face cards 11-12 the value of 10
    // ordinal is order in series, returns the ordinal of this enumeration constant
    public int getCardValue() {
        int value = this.mRank.ordinal() + 1;
        if (value > 10) value = 10;
        return value;
    }

    @Override
    public String toString() {
        return this.mRank + " of " + this.mSuit;
    }
}


