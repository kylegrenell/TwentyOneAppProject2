package com.example.user.twentyone.TwentyOne;

import android.os.AsyncTask;

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
        return players.get(currentPlayer);
    }


    // when start up a new game that will get everybody card and play first round
    // check the table state of each round . If it's a new game it will loop through, else it will
    // ask the players for their response. Once response given it goes back in and checks the loop

    public void checkTable(int callPlayer) {

        switch (tableState) {
            case NEW_GAME: {
                while (dealer.getCardCount() < 2) {
                    for (int i = 0; i < players.size(); i++)
                        players.get(i).hit(deck.dealCard());

                    dealer.hit(deck.dealCard());
                }
                currentPlayer = 0;
                tableState = State.PLAYING;
            }

//          state of Playing, ask players for an action
            case PLAYING: {

                    if (players.get(currentPlayer).getState() != Player.State.STAND ||
                            players.get(currentPlayer).getState() != Player.State.BUST) {

                        // loops for action to see if it's hit, bust or stand
                        if (players.get(currentPlayer).askAction() == Player.Action.HIT) {
                            players.get(currentPlayer).hit(deck.dealCard());

//                               checks if the current player is bust if not then increment++
                            if (players.get(currentPlayer).getHandValue() > 21) {
                                players.get(currentPlayer).setState(Player.State.BUST);
                                currentPlayer++;
                            }
                        }
                        // check if the player stands or if we want to increment the current player by 1 (++)
                        if (players.get(currentPlayer).askAction() == Player.Action.STAND) {
                            players.get(currentPlayer).setState(Player.State.STAND);
                            currentPlayer++;
                        }
                }
                if (currentPlayer > players.size() - 1)
                    tableState = RESOLVE;
            }


            case RESOLVE: {
                while (dealer.getHandValue() < 17)
                    dealer.hit(deck.dealCard());
            }
            default:
                break;
        }
    }




        // take in the list of current players and output the table

        public void clearTable()
        {
            for (int i = 0; i < players.size(); i++)
            {
                players.get(i).showHand();
            }
            dealer.showHand();
        }
    }


