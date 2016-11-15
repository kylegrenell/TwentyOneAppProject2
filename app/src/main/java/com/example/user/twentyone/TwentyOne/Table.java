package com.example.user.twentyone.TwentyOne;

import android.os.AsyncTask;
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
    int currentPlayer;


    public enum State{
        NEW_GAME,
        PLAYING,
        PAUSE,
        RESOLVE
    }


    public Table(){
        tableState = NEW_GAME;
        players = new ArrayList<Player>();
        dealer = new Player("Dealer");
        deck = new Deck();
    }

    // player can join table
    public void sitAtTable(Player player){
        players.add(player);
    }

    // can leave the table
    public void leaveTable(Player player){
        players.remove(player);
    }


    // getter for current player
    public Player getCurrentPlayer(){
        if(currentPlayer > players.size() -1)
        return players.get(currentPlayer - 1);
        else
            return players.get(currentPlayer);
    }


//    getter for the dealer
    public Player getDealer(){
        return dealer;
    }


    // when start up a new game that will get everybody card and play first round
    // check the table state of each round . If it's a new game it will loop through, else it will
    // ask the players for their response. Once response given it goes back in and checks the loop

    public void checkTable() {

        switch (tableState) {
            case NEW_GAME: {
                // players start at zero
                currentPlayer = 0;
                // reset each game - clear the hand of the previous cards in the loop
                dealer.clearHand();
                // debugged here, found that the game wasn't restting the state and so cards kept incrementing past 21,
                // added the below
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
                break;
            }

//          state of Playing, ask players for an action
            case PLAYING: {

                if (players.get(currentPlayer).getState() != Player.State.STAND ||
                        players.get(currentPlayer).getState() != Player.State.BUST) {

                    // loops for action to see if it's hit, bust or stand
                    if (players.get(currentPlayer).askAction() == Player.Action.HIT) {
                        players.get(currentPlayer).hit(deck.dealCard());

                        //  checks if the current player is bust if not then increment++
                        if (players.get(currentPlayer).getHandValue() > 21) {
                            players.get(currentPlayer).setState(Player.State.BUST);
                        }
                        else if (players.get(currentPlayer).getHandValue() == 21) {
                            players.get(currentPlayer).setState(Player.State.STAND);
                        }
                    }
                    // check if the player stands or if we want to increment the current player by 1 (++)
                    if (players.get(currentPlayer).askAction() == Player.Action.STAND)
                    {
                        players.get(currentPlayer).setState(Player.State.STAND);
                        currentPlayer++;
                    }
                    else if (players.get(currentPlayer).getState() != Player.State.BUST)
                    {
                        currentPlayer++;
                    }
                    // action for the player to wait
                    players.get(currentPlayer).setAction(Player.Action.WAIT);
                }
                //if the players hand is more than allowed hand size, resolve game
                if (currentPlayer > players.size() - 1)
                    tableState = RESOLVE;
                    // MISSING A BREAK HERE! took forever to debug
                else
                    break;
            }

            case RESOLVE:
            {
                // if the dealer is less than 17 they have to draw a card, hit deck...
                while (dealer.getHandValue() < 17)
                    dealer.hit(deck.dealCard());

                /////// show winner result ////////

                if (dealer.getState() == Player.State.BUST) {
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getState() != Player.State.BUST) {
                            players.get(i).setState(Player.State.WON);
                        }
                    }
                }
                else
                {

                    for (int i = 0; i < players.size(); i++) {
                        // check if the players is not (!=) bust
                        if (players.get(i).getState() != Player.State.BUST) {

                            // if not bust then check these things...
                            // so if a players hand value is more or less than dealers, the state of play should be
                            // passed in to the loop
                            // if the players hand value is less than the dealers hand player LOST
                            // else WON

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



    public void startNewGame(){
        // if table state does not equal state not playing then table state equals new game
        if(tableState != State.PLAYING)
        tableState = State.NEW_GAME;
    }


        // take in the list of current players and output the table

        public void clearTable()
        {
            for (int i = 0; i < players.size(); i++)
            {
                Log.v(String.valueOf(i),String.valueOf(players.get(i).getHandValue() ) );
            }
            Log.v("Dealer",String.valueOf(dealer.getHandValue() ) );
        }
    }