package com.example.user.twentyone.TwentyOne;

import android.util.Log;

import java.util.ArrayList;

import static com.example.user.twentyone.TwentyOne.Table.State.NEW_GAME;
import static com.example.user.twentyone.TwentyOne.Table.State.RESOLVE;

/**
 * Created by user on 14/11/2016.
 */

public class Table {

    ArrayList<Player> players;
    Player dealer;
    Deck deck;
    State tableState;
    int currentPlayerIndex;


    public enum State {
        NEW_GAME,
        PLAYING,
        PAUSE,
        RESOLVE
    }


    public Table() {
        tableState = NEW_GAME;
        players = new ArrayList<Player>();
        dealer = new Player("Dealer");
        deck = new Deck();
    }

    // player can join table
    public void sitAtTable(Player player) {
        players.add(player);
    }

    // can leave the table
    public void leaveTable(Player player) {
        players.remove(player);
    }


    // getter for current player
    public Player getCurrentPlayerIndex() {
        if (currentPlayerIndex > players.size() - 1)
            return players.get(currentPlayerIndex - 1);
        else
            return players.get(currentPlayerIndex);
    }


    //    getter for the dealer
    public Player getDealer() {
        return dealer;
    }


    // check if the table hand has finished
    public boolean checkTableFinished(){
        if(tableState == State.RESOLVE) {
            return true;
        }
        else {
            return false;
        }
    }


    // when start up a new game that will get everybody card and play first round
    // check the table state of each round . If it's a new game it will loop through, else it will
    // ask the players for their response. Once response given it goes back in and checks the loop


    private void setUpNewGame() {
        // players start at zero, initialise the deck, reset each game - clear the hand
        currentPlayerIndex = 0;
        deck.initialiseDeck();
        dealer.clearHand();

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setState(Player.State.PLAYING);
            players.get(i).clearHand();
        }

        while (dealer.getCardCount() < 2) {
            for (int i = 0; i < players.size(); i++)
                players.get(i).hit(deck.dealCard());
            dealer.hit(deck.dealCard());
        }

        tableState = State.PLAYING;
    }


    public void checkTable() {

        switch (tableState) {

            case NEW_GAME:
                setUpNewGame();
                break;

//          state of Playing, ask players for an action
            case PLAYING: {

                Player currentPlayer = players.get(currentPlayerIndex);
                Player.State currentState =  currentPlayer.getState();

                if (currentState != Player.State.STAND ||
                        currentState != Player.State.BUST) {
                    // loops for action to see if it's hit, bust or stand
                    if (players.get(currentPlayerIndex).askAction() == Player.Action.HIT) {
                        players.get(currentPlayerIndex).hit(deck.dealCard());
                        //  checks if the current player is bust if not then increment++
                        if (players.get(currentPlayerIndex).getHandValue() > 21) {
                            players.get(currentPlayerIndex).setState(Player.State.BUST);
                        } else if (players.get(currentPlayerIndex).getHandValue() == 21) {
                            players.get(currentPlayerIndex).setState(Player.State.STAND);
                        }
                    }
                    // check if the player stands or if we want to increment the current player by 1 (++)
                    if (players.get(currentPlayerIndex).askAction() == Player.Action.STAND) {
                        players.get(currentPlayerIndex).setState(Player.State.STAND);
                        players.get(currentPlayerIndex).setAction(Player.Action.WAIT);
                        currentPlayerIndex++;
                    } else if (players.get(currentPlayerIndex).getState() != Player.State.BUST)
                    {
                        players.get(currentPlayerIndex).setAction(Player.Action.WAIT);
                        currentPlayerIndex++; // action for the player to wait
                    }
                }
                if (currentPlayerIndex > players.size() - 1)
                    tableState = RESOLVE;
                else
                    break; // MISSING A BREAK HERE! took forever to debug
            }


            case RESOLVE: {
                // if the dealer is less than 17 they have to draw a card, hit deck...
                while (dealer.getHandValue() < 17)
                    dealer.hit(deck.dealCard());



                /////// show winner result ////////
//                    if dealer busts that resolves the game
                if (dealer.getState() == Player.State.BUST) {
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getState() != Player.State.BUST) {
                            players.get(i).setState(Player.State.WON);
                        }
                    }
                }
                else
                {
                    // if not bust then check these things...
                    // so if a players hand value is more or less than dealers, pass the state of play into loop
                    // if the players hand value is less than the dealers hand player Lose, else Win
                    // check if the players is not (!=) bust

                    for (int i = 0; i < players.size(); i++) {

                        if (players.get(i).getState() != Player.State.BUST) {

                            // Lost < less than dealer but not bust
                            if (players.get(i).getHandValue() < dealer.getHandValue())
                                players.get(i).setState(Player.State.LOST);
                            // won > more than dealer but not bust
                            if (players.get(i).getHandValue() < dealer.getHandValue())
                                players.get(i).setState(Player.State.WON);
                            // drawn / pushed - their stake is returned to them and they neither win nor lose
                            if (players.get(i).getHandValue() == dealer.getHandValue())
                                players.get(i).setState(Player.State.PUSH);
                        }
                    }
                }
            }
            default:
                break;
        }
    }



    public boolean startNewGame() {
        // if table state does not equal state not playing then table state equals new game
        if (tableState != State.PLAYING)
            tableState = State.NEW_GAME;

        return tableState != State.PLAYING;
    }



    // take in the list of current players and output the table

    public void clearTable() {
        for (int i = 0; i < players.size(); i++) {
            Log.v(String.valueOf(i), String.valueOf(players.get(i).getHandValue()));
        }
        Log.v("Dealer", String.valueOf(dealer.getHandValue()));
    }


//////// print results //////////


//Notice how the code declares a variable within the initialization expression. The scope of this variable extends from its
// declaration to the end of the block governed by the for statement, so it can be used in the termination and increment
// expressions as well. If the variable that controls a for statement is not needed outside of the loop, it's best to declare
// the variable in the initialization expression. The names i, j, and k are often used to control for loops; declaring them within
// the initialization expression limits their life span and reduces errors.
//
// The three expressions of the for loop are optional; an infinite loop can be created as follows:
//
/// infinite loop --
//
// for ( ; ; ) {
//
//        // your code goes here
//    }


    public String printResults() {
        String result = "";
        // add dealer state
        for (int i = 0; i < players.size(); i++) {
            // concatenate / chain string with name and results
            result += "Player: " + players.get(i).getName() + " " + players.get(i).getState().toString();
        }
        return "";
    }
}