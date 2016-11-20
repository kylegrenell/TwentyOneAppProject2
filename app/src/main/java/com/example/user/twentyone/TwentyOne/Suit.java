package com.example.user.twentyone.TwentyOne;

/**
 * Created by user on 15/11/2016.
 */

public enum Suit{
    HEARTS("Hearts"),
    SPADES("Spades"),
    CLUBS("Clubs"),
    DIAMONDS("Diamonds");

    private String stringValue;

    Suit(String stringValue){
        this.stringValue = stringValue;
    }

    public String getStringValue(){
        return stringValue;
    }
}