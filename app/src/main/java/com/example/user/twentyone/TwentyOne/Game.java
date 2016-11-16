package com.example.user.twentyone.TwentyOne;

import android.util.Log;

import java.util.ArrayList;

import static com.example.user.twentyone.TwentyOne.Game.State.NEW_GAME;
import static com.example.user.twentyone.TwentyOne.Game.State.RESOLVE;

/**
 * Created by user on 14/11/2016.
 */

public class Game {

    private ArrayList<Player> players;
    private Player dealer;
    private Deck deck;
    private State tableState;
    int currentPlayerIndex;

    public enum State {
        NEW_GAME,
        PLAYING,
        RESOLVE
    }

    public Game() {
        this.tableState = NEW_GAME;
        this.players = new ArrayList<Player>();
        this.dealer = new Player("Dealer");
        this.deck = new Deck();
    }

    // ----------- player can join table
    public void sitAtTable(Player player) {
        players.add(player);
    }

    // ----------- can leave the table
    public void leaveTable(Player player) {
        players.remove(player);
    }

    // ----------- getter for current player
    public Player getCurrentPlayerIndex() {
        if (currentPlayerIndex > players.size() - 1)
            return players.get(currentPlayerIndex - 1);
        else
            return players.get(currentPlayerIndex);
    }

    //  ----------  getter for the dealer
    public Player getDealer() {
        return dealer;
    }


    // ----------- check if the table hand has finished
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

// ------------- state of Playing, ask players for an action
            case PLAYING: {

                Player currentPlayer = players.get(currentPlayerIndex);
                Player.State currentState =  currentPlayer.getState();
                // loops for players action to see if it's hit, bust or stand
                //  checks if the current player is bust if not then increment++

                if (currentState != Player.State.STAND ||
                        currentState != Player.State.BUST) {

                    if (currentPlayer.askAction() == Player.Action.HIT) {
                        currentPlayer.hit(deck.dealCard());

                        if (currentPlayer.getHandValue() > 21) {
                            currentPlayer.setState(Player.State.BUST);

                        } else if (currentPlayer.getHandValue() == 21) {
                            currentPlayer.setState(Player.State.STAND);
                        }
                    } // check if the player stands, else increment the current player by 1 (++)
                    if (currentPlayer.askAction() == Player.Action.STAND) {
                        currentPlayer.setState(Player.State.STAND);
                        currentPlayer.setAction(Player.Action.WAIT);
                        currentPlayerIndex++;
                    }
                    else if (currentState != Player.State.BUST) {
                        currentPlayer.setAction(Player.Action.WAIT);
                        currentPlayerIndex++; // action for the player to wait
                    }
                }
                if (currentPlayerIndex > players.size() - 1)
                    tableState = RESOLVE;
                else
                    break;
            }

            case RESOLVE: {

// -------------- if the dealer is less than 17 they have to draw a card, hit deck...
                while (dealer.getHandValue() < 17)
                    dealer.hit(deck.dealCard());

//                resolve dealer hand
                if(dealer.getHandValue() > 21)
                    dealer.setState(Player.State.BUST);

// --------------- show winner result
                if (dealer.getState() == Player.State.BUST) {

                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getState() != Player.State.BUST) {

                            players.get(i).setState(Player.State.WON);
                        }
                    }
                }
// not bust, check players hand value > or < than dealers and not bust. If hand < dealers hand Lose, > Win.
                else {
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getState() != Player.State.BUST) {
                            if (players.get(i).getHandValue() < dealer.getHandValue())
                                players.get(i).setState(Player.State.LOST);
                            if (players.get(i).getHandValue() < dealer.getHandValue())
                                players.get(i).setState(Player.State.WON);
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

    // ----------------- start a new game
    public boolean startNewGame() {
        if (tableState != State.PLAYING)
            tableState = State.NEW_GAME;
        return tableState != State.PLAYING;
    }

    // ----------------- take in the list of current players and output the table
    public void clearTable() {
        for (int i = 0; i < players.size(); i++) {
            Log.v(String.valueOf(i), String.valueOf(players.get(i).getHandValue()));
        }
        Log.v("Dealer", String.valueOf(dealer.getHandValue()));
    }


    public String printResults()
    {
        String result = "";

        for (int i = 0; i < players.size(); i++)
        {
            // concatenate / chain string with name and results
            result += "Player: " + players.get(i).getName() + " " + players.get(i).getState().toString();
        }
        return result;
    }
}