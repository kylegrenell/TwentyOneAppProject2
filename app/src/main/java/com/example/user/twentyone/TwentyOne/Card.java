package com.example.user.twentyone.TwentyOne;

/**
 * Created by user on 14/11/2016.
 */

public class Card {

    private Rank rank;
    private Suit suit;


    public Card(Suit suit, Rank rank) {
        this.rank = rank;
        this.suit = suit;
    }

    // this should make the face cards 11-12 the value of 10
    // ordinal is order in series, returns the ordinal of this enumeration constant
    public int getCardValue() {
        int value = this.rank.ordinal() + 1;
        if (value > 10) value = 10;
        return value;
    }

//    get suit
    public Suit getSuit(){
        return this.suit;
    }

//    get rank
    public Rank getRank(){
        return this.rank;
    }

    //  override? - override a method in the superclass... nope.
    public String toString() {
        return rank.name() + " of " + this.suit.name();
    }

    public String getCardName(){
        return Integer.toString(rank.getNumberValue()) + " of " + suit.getStringValue();
    }
}

