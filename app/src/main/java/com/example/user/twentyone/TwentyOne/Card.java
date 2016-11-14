package com.example.user.twentyone.TwentyOne;

/**
 * Created by user on 14/11/2016.
 */

public class Card {

    private enum Suit{
        SPADE,
        HEART,
        CLUB,
        DIAMOND
    }

    private enum Rank{
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    private Suit suit;
    private Rank rank;

    public Card(int s, int r){
        suit = Suit.values()[s];
        rank = Rank.values()[r];
    }

    @Override
    public String toString(){
        return suit + " " + rank;
    }


}
