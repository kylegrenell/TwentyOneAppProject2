package com.example.user.twentyone.TwentyOne;

/**
 * Created by user on 15/11/2016.
 */

public enum Rank {
        ACE(11),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10);

        private int numberValue;
        Rank(int numVal){
                this.numberValue = numVal;
        }

        public int getNumberValue(){
                return numberValue;
        }

//        private String mTitle;

//        Rank( String title ){
//                mTitle = title;
//        }

//        public String getTitle(){
//                return mTitle;
//        }
//
//        public String toString() {
//                return this.mTitle;
//        }


}